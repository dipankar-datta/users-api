package com.example.data.serviceclient;

import com.example.ui.model.AlbumResponse;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "albums-ws", fallbackFactory = AlbumsServiceFallBackFactory.class)
public interface AlbumServiceClient {

    @GetMapping("/users/{id}/albums")
    public List<AlbumResponse> getAlbums(@PathVariable String id);
}

@Component
class AlbumsServiceFallBackFactory implements FallbackFactory<AlbumServiceClient> {
    @Override
    public AlbumServiceClient create(Throwable cause) {
        return new AlbumsServiceClientFallBack(cause);
    }
}

class AlbumsServiceClientFallBack implements AlbumServiceClient {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Throwable cause;

    public AlbumsServiceClientFallBack(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public List<AlbumResponse> getAlbums(String id) {

        if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
            logger.error("XXX---> 404 error took place when getAlbums was called with user ID: "
                    + id + ", Error message: " + cause.getLocalizedMessage());
        } else {
            logger.error("XXX---> Other error took place: " + cause.getLocalizedMessage());
        }

        return new ArrayList<>();
    }
}
