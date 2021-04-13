package at.ac.htlstp.deimel.springbootdemoserver.dto.maxima;

import lombok.Data;

@Data
public class MaximaDTO {
    
    private int index;
    private String input;
    private String output;

    public MaximaDTO(int index, String input, String output) {
        this.index = index;
        this.input = input;
        this.output = output;
    }

}
