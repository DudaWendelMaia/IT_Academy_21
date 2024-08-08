$(document).ready(function() {
    showSection('cadastro');

    $('#cadastroForm').submit(async function(event) {
        event.preventDefault();
        if (this.checkValidity() === false) {
            event.stopPropagation();
        } else {
            const teamCreateDTO = {
                name: $('#teamName').val(),
                warCry: $('#warCry').val(),
                foundationYear: parseInt($('#foundationYear').val())
            };

            try {
                const response = await fetch('/teams', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(teamCreateDTO)
                });

                if (response.ok) {
                    const result = await response.json();
                    $('#cadastroResult').text(`Time ${result.name} cadastrado com sucesso!`).addClass('alert alert-success');
                    $('#cadastroForm')[0].reset();
                    $('#cadastroForm').removeClass('was-validated');
                } else {
                    $('#cadastroResult').text('Erro ao cadastrar time.').addClass('alert alert-danger');
                }
            } catch (error) {
                $('#cadastroResult').text(`Erro ao cadastrar time: ${error.message}`).addClass('alert alert-danger');
            }
        }
        $('#cadastroForm').addClass('was-validated');
    });
});

function showSection(sectionId) {
    $('.content-section').removeClass('active');
    $(`#${sectionId}`).addClass('active');

    $('.nav-link').removeClass('active');
    $(`.nav-link[onclick="showSection('${sectionId}')"]`).addClass('active');

    if (sectionId === 'partida') loadMatches();
    else if (sectionId === 'fases') loadPhases();
    else if (sectionId === 'final') loadFinalResults();
}

async function loadMatches() {
    try {
        const response = await fetch('/matches');
        const matches = await response.json();
        const partidaPanel = $('#partidaPanel');
        partidaPanel.empty();

        matches.forEach(match => {
            if (!match.finished) {
                const matchElement = $(`
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">${match.teamA.name} vs ${match.teamB.name}</h5>
                            <p class="card-text">Pontos: ${match.teamA.name} - ${match.pointsTeamA} | ${match.teamB.name} - ${match.pointsTeamB}</p>
                            <button class="btn btn-primary mr-1" onclick="registerAction(${match.id}, 'blot', 'A')">Registrar Blot para ${match.teamA.name}</button>
                            <button class="btn btn-primary mr-1" onclick="registerAction(${match.id}, 'blot', 'B')">Registrar Blot para ${match.teamB.name}</button>
                            <button class="btn btn-secondary mr-1" onclick="registerAction(${match.id}, 'plif', 'A')">Registrar Plif para ${match.teamA.name}</button>
                            <button class="btn btn-secondary mr-1" onclick="registerAction(${match.id}, 'plif', 'B')">Registrar Plif para ${match.teamB.name}</button>
                            <button class="btn btn-danger" onclick="finishMatch(${match.id})">Encerrar Partida</button>
                        </div>
                    </div>
                `);
                partidaPanel.append(matchElement);
            }
        });
    } catch (error) {
        $('#partidaPanel').text(`Erro ao carregar partidas: ${error.message}`).addClass('alert alert-danger');
    }
}

async function startChampionship() {
    try {
        const response = await fetch('/championship/start', { method: 'POST' });

        if (response.ok) {
            $('#inicioResult').text('Campeonato iniciado com sucesso!').addClass('alert alert-success');
        } else {
            $('#inicioResult').text('Erro ao iniciar campeonato.').addClass('alert alert-danger');
        }
    } catch (error) {
        $('#inicioResult').text(`Erro ao iniciar campeonato: ${error.message}`).addClass('alert alert-danger');
    }
}

async function registerAction(matchId, action, team) {
    try {
        const url = `/matches/${matchId}/${action}/${team}`;
        const response = await fetch(url, { method: 'POST' });

        if (response.ok) loadMatches();
        else alert('Erro ao registrar ação.');
    } catch (error) {
        alert(`Erro ao registrar ação: ${error.message}`);
    }
}

async function finishMatch(matchId) {
    try {
        const response = await fetch(`/matches/${matchId}/finish`, { method: 'POST' });

        if (response.ok) loadMatches();
        else alert('Erro ao encerrar partida.');
    } catch (error) {
        alert(`Erro ao encerrar partida: ${error.message}`);
    }
}

async function loadPhases() {
    try {
        const response = await fetch('/phases');
        const phases = await response.json();
        const fasesResult = $('#fasesResult');
        fasesResult.empty();

        phases.forEach(phase => {
            const phaseElement = $(`
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Fase ${phase.id}</h5>
                        ${phase.matches.map(match => `
                            <p class="card-text">${match.teamA.name} vs ${match.teamB.name} - ${match.pointsTeamA} : ${match.pointsTeamB} - ${match.finished ? 'Finalizada' : 'Pendente'}</p>
                        `).join('')}
                        ${phase.complete ? '' : `<button class="btn btn-success" onclick="completePhase(${phase.id})">Completar Fase</button>`}
                    </div>
                </div>
            `);
            fasesResult.append(phaseElement);
        });

        const nextPhaseButton = $('<button class="btn btn-primary">Criar Próxima Fase</button>');
        nextPhaseButton.click(createNextPhase);
        fasesResult.append(nextPhaseButton);

    } catch (error) {
        $('#fasesResult').text(`Erro ao carregar fases: ${error.message}`).addClass('alert alert-danger');
    }
}

async function createNextPhase() {
    try {
        const response = await fetch('/phases/next', { method: 'POST' });

        if (response.ok) loadPhases();
        else alert('Erro ao criar próxima fase.');
    } catch (error) {
        alert(`Erro ao criar próxima fase: ${error.message}`);
    }
}

async function completePhase(phaseId) {
    try {
        const response = await fetch(`/phases/${phaseId}/complete`, { method: 'POST' });

        if (response.ok) loadPhases();
        else alert('Erro ao completar fase.');
    } catch (error) {
        alert(`Erro ao completar fase: ${error.message}`);
    }
}

async function loadFinalResults() {
    try {
        const resultsResponse = await fetch('/championship/results');
        const results = await resultsResponse.json();

        const championResponse = await fetch('/championship/champion');
        const champion = await championResponse.json();

        const finalResultDiv = $('#finalResult');
        finalResultDiv.empty();
        finalResultDiv.append(`
            <h3>Resultado Final</h3>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Time</th>
                        <th>Pontos</th>
                        <th>Total de Blots</th>
                        <th>Total de Plifs</th>
                        <th>Total de Advrunghs</th>
                    </tr>
                </thead>
                <tbody>
                    ${results.map(team => `
                        <tr>
                            <td>${team.name}</td>
                            <td>${team.points}</td>
                            <td>${team.totalBlots}</td>
                            <td>${team.totalPlifs}</td>
                            <td>${team.totalAdvrunghs}</td>
                        </tr>
                    `).join('')}
                </tbody>
            </table>
            <h3>Campeão: ${champion.name}</h3>
            <p>Grito de Guerra: ${champion.warCry}</p>
        `);
    } catch (error) {
        $('#finalResult').text(`Erro ao carregar resultados finais: ${error.message}`).addClass('alert alert-danger');
    }
}
