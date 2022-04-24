package remora.remora.Classification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import remora.remora.Classification.dto.ClassificationRequestDto;
import remora.remora.Classification.dto.ClassificationResponseDto;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ClassificationController {
    private final ClassificationService classificationService;
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public ClassificationController(ClassificationService classificationService) {
        this.classificationService = classificationService;
    }

    @PostMapping(value = "/keywords", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ClassificationResponseDto classification(@RequestBody @Valid ClassificationRequestDto request) {
        ClassificationResponseDto response = new ClassificationResponseDto();

        try {
            for (int i = 0; i < request.translatedText.size(); i++) {
                response.translatedText.add(request.translatedText.get(i));
                List<String> result = classificationService.classification(request.translatedText.get(i));
                response.keywords.add(result);

                log.info("Classification result");
                for (String s : result) {
                    log.info("{}", s);
                }
            }
            log.info("Classification success");
            response.success = true;
            response.message = "Success";

        } catch (Exception e) {
            e.printStackTrace();
            log.info("Classification fail={}", e.getMessage());
            response.success = false;
            response.message = e.getMessage();
            response.translatedText = null;
            response.keywords = null;
        }

        return response;
    }
}
