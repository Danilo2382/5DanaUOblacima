package org.springbootapp.petdanauoblacima.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PlayerRequest {

    @NotBlank(message = "Nickname can't be null or empty")
    private String nickname;

}
