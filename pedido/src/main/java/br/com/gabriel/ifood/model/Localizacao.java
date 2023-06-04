package br.com.gabriel.ifood.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Localizacao {

    public Double latitude;

    public Double longitude;
}
