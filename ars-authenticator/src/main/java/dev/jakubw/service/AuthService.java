package dev.jakubw.service;

import dev.jakubw.dto.SignInRequest;
import dev.jakubw.dto.ProviderSignUpRequest;
import dev.jakubw.dto.UserSignUpRequest;
import dev.jakubw.exception.SignUpException;
import dev.jakubw.grpc.*;
import dev.jakubw.grpc.ProviderRegisterServiceGrpc.ProviderRegisterServiceBlockingStub;
import dev.jakubw.grpc.UserRegisterServiceGrpc.UserRegisterServiceBlockingStub;
import dev.jakubw.model.AuthDetailsEntity;
import dev.jakubw.repository.AuthDetailsRepository;
import io.github.resilience4j.retry.annotation.Retry;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthDetailsRepository authUserDetailsRepository;
    private final ProviderRegisterServiceBlockingStub stubP;
    private final UserRegisterServiceBlockingStub stubU;
    private final UserService userService;

    public String login(SignInRequest request){
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.email(),request.password()));

        AuthDetailsEntity userDetails = (AuthDetailsEntity) auth.getPrincipal();

        return jwtService.generateToken(userDetails);
    }

    @Retry(name = "user-register")
    public String registerUser(UserSignUpRequest request){
        if(authUserDetailsRepository.existsByEmailOrUsername(request.email(), request.username())){
            throw new SignUpException("User with this username or email already exists.");
        }
        UserRegisterRpcRequest rpcRequest;
        UserRegisterRpcResponse response;
        try{
            List<RpcAdTag> rpcTags = request.tags().stream().map(tag -> RpcAdTag.valueOf(tag.name())).toList();

            rpcRequest = UserRegisterRpcRequest.newBuilder()
                    .setEmail(request.email())
                    .setUsername(request.username())
                    .addAllTags(rpcTags)
                    .build();
            response = stubU.register(rpcRequest);
        }catch (StatusRuntimeException e){
            throw new SignUpException("Could register: " + e.getMessage(), e);
        }

        AuthDetailsEntity saved = userService.saveUser(request, response.getId());
        return jwtService.generateToken(saved);
    }


    @Retry(name = "provider-register")
    public String registerProvider(ProviderSignUpRequest request){
        if(authUserDetailsRepository.existsByEmailOrUsername(request.email(), request.username())){
            throw new SignUpException("Provider with this username or email already exists.");
        }
        ProviderRegisterRpcRequest rpcRequest;
        ProviderRegisterRpcResponse response;

        try{
            rpcRequest = ProviderRegisterRpcRequest.newBuilder()
                    .setName(request.name())
                    .build();
            response = stubP.register(rpcRequest);
        }catch (StatusRuntimeException e){
            throw new SignUpException("Could register: " + e.getMessage(), e);
        }

        AuthDetailsEntity saved = userService.saveProvider(request,response.getId());
        return jwtService.generateToken(saved);
    }
}
