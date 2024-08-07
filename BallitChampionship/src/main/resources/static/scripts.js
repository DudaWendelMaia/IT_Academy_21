document.addEventListener('DOMContentLoaded', () => {
    showSection('cadastro');

    const cadastroForm = document.getElementById('cadastroForm');
    cadastroForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        const teamName = document.getElementById('teamName').value;
        const warCry = document.getElementById('warCry').value;
        const foundationYear = document.getElementById('foundationYear').value;

        const teamCreateDTO = {
            name: teamName,
            warCry: warCry,
            foundationYear: parseInt(foundationYear)
        };

        try {
            const response = await fetch('/teams', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(teamCreateDTO)
            });

            if (response.ok) {
                const result = await response.json();
                document.getElementById('cadastroResult').innerText = `Time ${result.name} cadastrado com sucesso!`;
                cadastroForm.reset();
            } else {
                document.getElementById('cadastroResult').innerText = `Erro ao cadastrar time.`;
            }
        } catch (error) {
            document.getElementById('cadastroResult').innerText = `Erro ao cadastrar time: ${error.message}`;
        }
    });
});

function showSection(sectionId) {
    document.querySelectorAll('.content-section').forEach(section => {
        section.classList.remove('active');
    });
    document.getElementById(sectionId).classList.add('active');

    document.querySelectorAll('.nav-link').forEach(link => {
        link.classList.remove('active');
    });
    document.querySelector(`[onclick="showSection('${sectionId}')"]`).classList.add('active');

    if (sectionId === 'partida') {
        loadMatches();
    } else if (sectionId === 'fases') {
        loadPhases();
    } else if (sectionId === 'final') {
        loadFinalResults();
    }
}

async function loadMatches() {
    try {
        const response = await fetch('/matches');
        const matches = await response.json();
        const partidaPanel = document.getElementById('partidaPanel');
        partidaPanel.innerHTML = '';

        matches.forEach(match => {
            if (!match.finished) {
                const matchElement = document.createElement('div');
                matchElement.classList.add('card', 'mb-3');
                matchElement.innerHTML = `
                    <div class="card-body">
                        <h5 class="card-title">${match.teamA.name} vs ${match.teamB.name}</h5>
                        <p class="card-text">Pontos: ${match.teamA.name} - ${match.pointsTeamA} | ${match.teamB.name} - ${match.pointsTeamB}</p>
                        <button class="btn btn-primary" onclick="registerAction(${match.id}, 'blot', 'A')">Registrar Blot para ${match.teamA.name}</button>
                        <button class="btn btn-primary" onclick="registerAction(${match.id}, 'blot', 'B')">Registrar Blot para ${match.teamB.name}</button>
                        <button class="btn btn-secondary" onclick="registerAction(${match.id}, 'plif', 'A')">Registrar Plif para ${match.teamA.name}</button>
                        <button class="btn btn-secondary" onclick="registerAction(${match.id}, 'plif', 'B')">Registrar Plif para ${match.teamB.name}</button>
                        <button class="btn btn-danger" onclick="finishMatch(${match.id})">Encerrar Partida</button>
                    </div>
                `;
                partidaPanel.appendChild(matchElement);
            }
        });
    } catch (error) {
        document.getElementById('partidaPanel').innerText = `Erro ao carregar partidas: ${error.message}`;
    }
}

async function registerAction(matchId, action, team) {
    try {
        let url = `/matches/${matchId}/${action}/${team}`;
        const response = await fetch(url, {
            method: 'POST'
        });

        if (response.ok) {
            loadMatches();
        } else {
            alert('Erro ao registrar ação.');
        }
    } catch (error) {
        alert(`Erro ao registrar ação: ${error.message}`);
    }
}

async function finishMatch(matchId) {
    try {
        const response = await fetch(`/matches/${matchId}/finish`, {
            method: 'POST'
        });

        if (response.ok) {
            loadMatches();
        } else {
            alert('Erro ao encerrar partida.');
        }
    } catch (error) {
        alert(`Erro ao encerrar partida: ${error.message}`);
    }
}

async function loadPhases() {
    try {
        const response = await fetch('/phases');
        const phases = await response.json();
        const fasesResult = document.getElementById('fasesResult');
        fasesResult.innerHTML = '';

        phases.forEach(phase => {
            const phaseElement = document.createElement('div');
            phaseElement.classList.add('card', 'mb-3');
            phaseElement.innerHTML = `
                <div class="card-body">
                    <h5 class="card-title">Fase ${phase.id}</h5>
                    ${phase.matches.map(match => `
                        <p class="card-text">${match.teamA.name} vs ${match.teamB.name} - ${match.pointsTeamA} : ${match.pointsTeamB} - ${match.finished ? 'Finalizada' : 'Pendente'}</p>
                    `).join('')}
                    ${phase.complete ? '' : `<button class="btn btn-success" onclick="completePhase(${phase.id})">Completar Fase</button>`}
                </div>
            `;
            fasesResult.appendChild(phaseElement);
        });

        const nextPhaseButton = document.createElement('button');
        nextPhaseButton.classList.add('btn', 'btn-primary');
        nextPhaseButton.innerText = 'Criar Próxima Fase';
        nextPhaseButton.onclick = createNextPhase;
        fasesResult.appendChild(nextPhaseButton);

    } catch (error) {
        document.getElementById('fasesResult').innerText = `Erro ao carregar fases: ${error.message}`;
    }
}

async function createNextPhase() {
    try {
        const response = await fetch('/phases/next', {
            method: 'POST'
        });

        if (response.ok) {
            loadPhases();
        } else {
            alert('Erro ao criar próxima fase.');
        }
    } catch (error) {
        alert(`Erro ao criar próxima fase: ${error.message}`);
    }
}

async function completePhase(phaseId) {
    try {
        const response = await fetch(`/phases/${phaseId}/complete`, {
            method: 'POST'
        });

        if (response.ok) {
            loadPhases();
        } else {
            alert('Erro ao completar fase.');
        }
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

        const finalResultDiv = document.getElementById('finalResult');
        finalResultDiv.innerHTML = `
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
        `;
    } catch (error) {
        document.getElementById('finalResult').innerText = `Erro ao carregar resultados finais: ${error.message}`;
    }
}
