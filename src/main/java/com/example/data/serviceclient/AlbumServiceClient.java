package com.example.data.serviceclient;

import com.example.ui.model.AlbumResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "albums-ws")
public interface AlbumServiceClient {

    @GetMapping("/users/{id}/albums")
    public List<AlbumResponse> getAlbums(@PathVariable String id);
}
