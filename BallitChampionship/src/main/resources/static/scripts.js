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

    if (sectionId === 'partida') {
        loadMatches();
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
                matchElement.innerHTML = `
                    <h3>${match.teamA.name} vs ${match.teamB.name}</h3>
                    <p>Pontos: ${match.teamA.name} - ${match.pointsTeamA} | ${match.teamB.name} - ${match.pointsTeamB}</p>
                    <button onclick="registerAction(${match.id}, 'blot', 'A')">Registrar Blot para ${match.teamA.name}</button>
                    <button onclick="registerAction(${match.id}, 'blot', 'B')">Registrar Blot para ${match.teamB.name}</button>
                    <button onclick="registerAction(${match.id}, 'plif', 'A')">Registrar Plif para ${match.teamA.name}</button>
                    <button onclick="registerAction(${match.id}, 'plif', 'B')">Registrar Plif para ${match.teamB.name}</button>
                    <button onclick="finishMatch(${match.id})">Encerrar Partida</button>
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

async function loadFinalResults() {
    try {
        const resultsResponse = await fetch('/championship/results');
        const results = await resultsResponse.json();

        const championResponse = await fetch('/championship/champion');
        const champion = await championResponse.json();

        const finalResultDiv = document.getElementById('finalResult');
        finalResultDiv.innerHTML = `
            <h3>Resultado Final</h3>
            <table>
                <tr>
                    <th>Time</th>
                    <th>Pontos</th>
                    <th>Total de Blots</th>
                    <th>Total de Plifs</th>
                    <th>Total de Advrunghs</th>
                </tr>
                ${results.map(team => `
                    <tr>
                        <td>${team.name}</td>
                        <td>${team.points}</td>
                        <td>${team.totalBlots}</td>
                        <td>${team.totalPlifs}</td>
                        <td>${team.totalAdvrunghs}</td>
                    </tr>
                `).join('')}
            </table>
            <h3>Campeão: ${champion.name}</h3>
            <p>Grito de Guerra: ${champion.warCry}</p>
        `;
    } catch (error) {
        document.getElementById('finalResult').innerText = `Erro ao carregar resultados finais: ${error.message}`;
    }
}
