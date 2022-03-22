package com.example.demo.model.lambda;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
@ToString
public class LambdaMailRequestSendgrid {

    private String emailFrom;

    private String subject;

    private String emailTo;

    private String content;
    
    private List<Attachment> archivos;

}
