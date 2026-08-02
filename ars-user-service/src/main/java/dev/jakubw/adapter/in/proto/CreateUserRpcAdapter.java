package dev.jakubw.adapter.in.proto;


import dev.jakubw.config.exception.SignUpException;
import dev.jakubw.domain.model.AdTag;
import dev.jakubw.domain.port.in.CreateUserCmd;
import dev.jakubw.domain.port.in.model.CreateUserCmdRequest;
import dev.jakubw.grpc.UserRegisterRpcRequest;
import dev.jakubw.grpc.UserRegisterRpcResponse;
import dev.jakubw.grpc.UserRegisterServiceGrpc.UserRegisterServiceImplBase;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@GrpcService
@RequiredArgsConstructor
public class CreateUserRpcAdapter extends UserRegisterServiceImplBase {

    private final CreateUserCmd command;

    @Override
    public void register(UserRegisterRpcRequest request, StreamObserver<UserRegisterRpcResponse> responseObserver) {
        try {
            Set<AdTag> tags = request.getTagsList().stream()
                    .map(rpcTag -> AdTag.valueOf(rpcTag.name()))
                    .collect(Collectors.toSet());

            CreateUserCmdRequest cmdRequest = new CreateUserCmdRequest(request.getUsername(), request.getEmail(), tags);

            String id = command.execute(cmdRequest).getId();
            UserRegisterRpcResponse response = UserRegisterRpcResponse.newBuilder()
                    .setId(id)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (SignUpException e) {
            responseObserver.onError(
                    Status.ALREADY_EXISTS
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        } catch (Exception e) {
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription("An unexpected error occurred while creating the user: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }
}
