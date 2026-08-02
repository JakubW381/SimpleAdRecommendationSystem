package dev.jakubw.config;

import dev.jakubw.grpc.ProviderRegisterServiceGrpc;
import dev.jakubw.grpc.UserRegisterServiceGrpc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;

@Configuration
public class GrpcChannelsConfig {
    @Bean
    UserRegisterServiceGrpc.UserRegisterServiceBlockingStub userStub(GrpcChannelFactory factory){
        return UserRegisterServiceGrpc.newBlockingStub(factory.createChannel("0.0.0.0:9082"));
    }
    @Bean
    ProviderRegisterServiceGrpc.ProviderRegisterServiceBlockingStub providerStub(GrpcChannelFactory factory){
        return ProviderRegisterServiceGrpc.newBlockingStub(factory.createChannel("0.0.0.0:9081"));
    }
}
