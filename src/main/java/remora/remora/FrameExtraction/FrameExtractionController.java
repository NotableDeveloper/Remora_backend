package remora.remora.FrameExtraction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import remora.remora.FrameExtraction.dto.FrameExtractionRequestDto;
import remora.remora.FrameExtraction.dto.FrameExtractionResponseDto;

import javax.validation.Valid;

@RestController
public class FrameExtractionController {
    private final FrameExtractionService frameExtractionService;
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public FrameExtractionController(FrameExtractionService frameExtractionService) {
        this.frameExtractionService = frameExtractionService;
    }

    @PostMapping(value = "/frames", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public FrameExtractionResponseDto frameExtract(@RequestBody @Valid FrameExtractionRequestDto request) {
        FrameExtractionResponseDto response = new FrameExtractionResponseDto();

        try {
            for (String path : request.videoCode) {
                response.frameSet.add(frameExtractionService.frameExtract(path));
            }
            log.info("Frame extraction success");
            response.success = true;
            response.message = "Success";
            response.videoCode = request.videoCode;

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Frame extraction fail={}", e.getMessage());
            response.success = false;
            response.message = e.getMessage();
            response.frameSet = null;
            response.videoCode = null;
        }

        return response;
    }
}
