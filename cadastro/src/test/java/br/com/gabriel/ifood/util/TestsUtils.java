package br.com.gabriel.ifood.util;

import br.com.gabriel.ifood.dto.RestauranteDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TestsUtils {

    public static List<RestauranteDTO> getContracts(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File arquivoJson = new File(path);
        List<RestauranteDTO> restauranteList = mapper.readValue(arquivoJson, new TypeReference<>(){});
        return restauranteList;
    }
}
