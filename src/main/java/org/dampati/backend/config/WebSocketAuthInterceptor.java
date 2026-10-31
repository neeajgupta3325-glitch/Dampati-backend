
package org.dampati.backend.config;

import org.dampati.backend.repository.UserRepository;
import org.dampati.backend.security.JwtService;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public WebSocketAuthInterceptor(
            JwtService jwtService,
            UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(
                        message, StompHeaderAccessor.class);

        if (accessor == null) {
            return message;
        }

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            List<String> headers =
                    accessor.getNativeHeader("Authorization");

            if (headers == null || headers.isEmpty()
                    || !headers.get(0).startsWith("Bearer ")) {
                throw new IllegalArgumentException("Missing WebSocket JWT");
            }

            String token = headers.get(0).substring(7);

            if (!jwtService.isTokenValid(token)) {
                throw new IllegalArgumentException("Invalid WebSocket JWT");
            }

            String email = jwtService.extractEmail(token);

            userRepository.findByEmail(email)
                    .orElseThrow(() ->
                            new IllegalArgumentException("User not found"));

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            email, null, List.of());

            accessor.setUser(authentication);
        }

        return message;
    }
}
