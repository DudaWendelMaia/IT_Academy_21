package com.MariaEduardaWendelMaia.BallitChampionship.documentation;

import com.MariaEduardaWendelMaia.BallitChampionship.dto.MatchDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface MatchControllerDoc {

    @Operation(summary = "Criar uma partida.", description = "Cria uma nova partida.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Partida criada com sucesso."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
            }
    )
    @PostMapping
    public ResponseEntity<MatchDTO> createMatch(@Valid @RequestBody MatchDTO matchDTO);

    @Operation(summary = "Listar partidas.", description = "Lista todas as partidas.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Lista de partidas retornada com sucesso."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
            }
    )
    @GetMapping
    public ResponseEntity<List<MatchDTO>> listMatches();

    @Operation(summary = "Atualizar uma partida.", description = "Atualiza uma partida pelo ID.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Partida atualizada com sucesso."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<MatchDTO> updateMatch(@PathVariable Integer id, @Valid @RequestBody MatchDTO matchDTO) throws Exception;

    @Operation(summary = "Deletar uma partida.", description = "Deleta uma partida pelo ID.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Partida deletada com sucesso."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Integer id) throws Exception;
}
