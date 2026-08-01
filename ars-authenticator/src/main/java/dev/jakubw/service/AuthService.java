package dev.jakubw.service;

import dev.jakubw.dto.SignInRequest;
import dev.jakubw.dto.SignUpRequest;
import dev.jakubw.exception.SignUpException;
import dev.jakubw.model.AuthDetailsEntity;
import dev.jakubw.repository.AuthDetailsRepository;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthDetailsRepository authUserDetailsRepository;
    private final UserRegistrationRpcServiceGrpc.UserRegistrationRpcServiceBlockingStub stubU;
    private final ProviderRegistrationRpcServiceGrpc.UserRegistrationRpcServiceBlockingStub stubP;
    private final UserService userService;

    public String loginUser(SignInRequest request){
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.email(),request.password()));

        AuthDetailsEntity userDetails = (AuthDetailsEntity) auth.getPrincipal();

        return jwtService.generateToken(userDetails);
    }

    public String registerUser(SignUpRequest request){
        if(authUserDetailsRepository.existsByEmailOrUsername(request.email(), request.username())){
            throw new SignUpException("User with this username or email already exists.");
        }
        UserRegisterRpcRequest rpcRequest;
        UserRegisterRpcResponse response;
        try{
            rpcRequest= UserRegisterRpcRequest.newBuilder()
                    .setEmail(request.email())
                    .setUsername(request.username())
                    .build();
            response = stub.register(rpcRequest);
        }catch (StatusRuntimeException e){
            throw new SignUpException("Could register: " + e.getMessage(), e);
        }

        AuthUserDetails saved = userService.saveUser(request, UUID.fromString(response.getUuid()));
        return jwtService.generateToken(saved);
    }

    public String loginProvider(SignInRequest request){
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.email(),request.password()));

        AuthDetailsEntity userDetails = (AuthDetailsEntity) auth.getPrincipal();

        return jwtService.generateToken(userDetails);
    }

    public String registerProvider(SignUpRequest request){
        if(authUserDetailsRepository.existsByEmailOrUsername(request.email(), request.username())){
            throw new SignUpException("User with this username or email already exists.");
        }
        UserRegisterRpcRequest rpcRequest;
        UserRegisterRpcResponse response;

        try{
            rpcRequest= UserRegisterRpcRequest.newBuilder()
                    .setEmail(request.email())
                    .setUsername(request.username())
                    .build();
            response = stub.register(rpcRequest);
        }catch (StatusRuntimeException e){
            throw new SignUpException("Could register: " + e.getMessage(), e);
        }

        AuthUserDetails saved = userService.saveUser(request, UUID.fromString(response.getUuid()));
        return jwtService.generateToken(saved);
    }
}
