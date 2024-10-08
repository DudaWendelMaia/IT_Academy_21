package com.MariaEduardaWendelMaia.BallitChampionship.documentation;

import com.MariaEduardaWendelMaia.BallitChampionship.dto.TeamCreateDTO;
import com.MariaEduardaWendelMaia.BallitChampionship.dto.TeamDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface TeamControllerDoc {

    @Operation(summary = "Criar uma equipe.", description = "Cria uma nova equipe.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Equipe criada com sucesso."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
            }
    )
    @PostMapping
    public ResponseEntity<TeamDTO> createTeam(@Valid @RequestBody TeamCreateDTO teamCreateDTO);

    @Operation(summary = "Listar equipes.", description = "Lista todas as equipes.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Lista de equipes retornada com sucesso."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
            }
    )
    @GetMapping
    public ResponseEntity<List<TeamDTO>> listTeams();

    @Operation(summary = "Atualizar uma equipe.", description = "Atualiza uma equipe pelo ID.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Equipe atualizada com sucesso."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<TeamDTO> updateTeam(@PathVariable Integer id, @Valid @RequestBody TeamCreateDTO teamCreateDTO) throws Exception;

    @Operation(summary = "Deletar uma equipe.", description = "Deleta uma equipe pelo ID.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Equipe deletada com sucesso."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Integer id) throws Exception;
}
