package com.MariaEduardaWendelMaia.BallitChampionship.documentation;

import com.MariaEduardaWendelMaia.BallitChampionship.dto.TeamDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface TeamControllerDoc {

    @Operation(summary = "Listar todos os times", description = "Lista todos os times do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de times"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
    })
    @GetMapping
    List<TeamDTO> getAllTeams();

    @Operation(summary = "Criar um novo time", description = "Cria um novo time no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Time criado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
    })
    @PostMapping
    TeamDTO createTeam(@RequestBody TeamDTO teamDTO);

    @Operation(summary = "Obter um time pelo ID", description = "Obtém um time do banco de dados pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Time obtido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Time não encontrado"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
    })
    @GetMapping("/{id}")
    TeamDTO getTeamById(@PathVariable Integer id);
}
