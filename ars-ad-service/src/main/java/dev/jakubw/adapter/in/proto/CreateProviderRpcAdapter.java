package dev.jakubw.adapter.in.proto;


import dev.jakubw.config.exception.SignUpException;
import dev.jakubw.domain.port.in.provider.RegisterAdProviderCmd;
import dev.jakubw.grpc.ProviderRegisterRpcRequest;
import dev.jakubw.grpc.ProviderRegisterRpcResponse;
import dev.jakubw.grpc.ProviderRegisterServiceGrpc.ProviderRegisterServiceImplBase;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

@Service
@GrpcService
@RequiredArgsConstructor
public class CreateProviderRpcAdapter extends ProviderRegisterServiceImplBase {

    private final RegisterAdProviderCmd command;

    @Override
    public void register(ProviderRegisterRpcRequest request, StreamObserver<ProviderRegisterRpcResponse> responseObserver) {
        try {
            String name = request.getName();
            String id = command.execute(name).getId();
            ProviderRegisterRpcResponse response = ProviderRegisterRpcResponse.newBuilder()
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
