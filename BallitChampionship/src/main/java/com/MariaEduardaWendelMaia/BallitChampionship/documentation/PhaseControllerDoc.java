package com.MariaEduardaWendelMaia.BallitChampionship.documentation;

import com.MariaEduardaWendelMaia.BallitChampionship.dto.PhaseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface PhaseControllerDoc {

    @Operation(summary = "Criar uma fase.", description = "Cria uma nova fase.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Fase criada com sucesso."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
            }
    )
    @PostMapping
    public ResponseEntity<PhaseDTO> createPhase(@Valid @RequestBody PhaseDTO phaseDTO);

    @Operation(summary = "Listar fases.", description = "Lista todas as fases.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Lista de fases retornada com sucesso."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
            }
    )
    @GetMapping
    public ResponseEntity<List<PhaseDTO>> listPhases();

    @Operation(summary = "Atualizar uma fase.", description = "Atualiza uma fase pelo ID.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Fase atualizada com sucesso."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<PhaseDTO> updatePhase(@PathVariable Integer id, @Valid @RequestBody PhaseDTO phaseDTO) throws Exception;

    @Operation(summary = "Deletar uma fase.", description = "Deleta uma fase pelo ID.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Fase deletada com sucesso."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhase(@PathVariable Integer id) throws Exception;
}
