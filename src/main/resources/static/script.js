// Fetch list of agents and display them
function loadAgents() {
    console.log('Loading agents...');
    fetch('/api/agents')  // Assuming your API for agents is /api/agents
        .then(response => {
            console.log('Response received from /api/agents');
            if (!response.ok) {
                console.error('Failed to fetch agents. Status:', response.status);
                throw new Error('Failed to fetch agents.');
            }
            return response.json();
        })
        .then(data => {
            console.log('Agents data received:', data);
            const agentsListDiv = document.getElementById('agentsList');
            agentsListDiv.innerHTML = ''; // Clear any previous content

            if (Array.isArray(data)) {
                console.log('Rendering agents...');
                data.forEach(agent => {
                    const agentElement = document.createElement('div');
                    const agentHeader = document.createElement('h3');
                    const agentDescription = document.createElement('p');

                    agentElement.classList.add('agent-item');
                    agentHeader.innerText = agent.name;
                    agentDescription.innerText = agent.description;

                    // Now when user clicks, redirect with agentId
                    agentElement.onclick = () => selectAgent(agent.id);

                    agentElement.appendChild(agentHeader);
                    agentElement.appendChild(agentDescription);
                    agentsListDiv.appendChild(agentElement);
                });
            } else {
                console.error('Invalid agents data:', data);
                alert('Error: Agents data format is incorrect.');
            }
        })
        .catch(error => {
            console.error('Error fetching agents:', error);
            alert('Failed to load agents. Please try again later.');
        });
}

// Select an agent and redirect to chat page using agentId
function selectAgent(agentId) {
    const encodedId = encodeURIComponent(agentId); // Safely encode ID
    window.location.href = `chat.html?agentName=${encodedId}`;
}

// Load the list of agents when the page is ready
window.onload = loadAgents;
