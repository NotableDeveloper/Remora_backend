package remora.remora.Api;

import org.springframework.web.bind.annotation.*;
import remora.remora.Api.dto.DeleteRequestDto;
import remora.remora.Api.dto.SimpleResponseDto;
import remora.remora.Api.dto.UploadRequestDto;
import remora.remora.Api.dto.UploadResponseDto;

import java.io.IOException;

@RestController
public class ApiController {

    private ApiService apiService = new ApiService();

    public UploadResponseDto uploadVideo(UploadRequestDto uploadReqDto) throws IOException {
        return apiService.uploadVideo(uploadReqDto);
    }

    public SimpleResponseDto changeVideo(UploadRequestDto uploadReqDto) throws IOException {
        return apiService.changeVideo(uploadReqDto);
    }

    public SimpleResponseDto deleteVideo(DeleteRequestDto deleteReqDto){
        return apiService.deleteVideo(deleteReqDto);
    }
}
