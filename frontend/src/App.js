import React, {useState, useEffect, useRef} from 'react';
import './App.css';
import IntersectionVisualizer from './components/IntersectionVisualizer';
import SimulationControls from './components/SimulationControls';
import SimulationHistory from './components/SimulationHistory';
import CommandPanel from './components/CommandPanel';

function App() {
    const [simulationData, setSimulationData] = useState(null);
    const [commands, setCommands] = useState([]);
    const [currentStep, setCurrentStep] = useState(0);

    const runSimulation = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/simulation/run', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({commands}),
            });

            const data = await response.json();
            setSimulationData(data);
            setCurrentStep(0);
        } catch (error) {
            console.error('Error running simulation:', error);
        }
    };

    const addCommand = (command) => {
        setCommands([...commands, command]);
    };

    const navigateStep = (step) => {
        if (simulationData && step >= 0 && step < simulationData.stepStatuses.length) {
            setCurrentStep(step);
        }
    };

    const handleFileUpload = async (file) => {
        const formData = new FormData();
        formData.append('file', file);

        try {
            const response = await fetch('http://localhost:8080/api/simulation/upload', {
                method: 'POST',
                body: formData,
            });

            const data = await response.json();
            setSimulationData(data);
            setCurrentStep(0);
        } catch (error) {
            console.error('Error uploading file:', error);
        }
    };

    return (
        <div className="app">
            <header>
                <h1>Symulator Skrzyżowania z Sygnalizacją Świetlną</h1>
            </header>

            <div className="app-container">
                <div className="left-panel">
                    <CommandPanel
                        commands={commands}
                        addCommand={addCommand}
                        runSimulation={runSimulation}
                        handleFileUpload={handleFileUpload}
                    />
                </div>

                <div className="center-panel">
                    {simulationData ? (
                        <IntersectionVisualizer
                            data={simulationData.stepStatuses[currentStep]}
                            step={currentStep}
                        />
                    ) : (
                        <div className="empty-state">
                            <p>Dodaj komendy lub wczytaj plik JSON, aby uruchomić symulację</p>
                        </div>
                    )}
                </div>

                <div className="right-panel">
                    {simulationData && (
                        <>
                            <SimulationControls
                                currentStep={currentStep}
                                totalSteps={simulationData.stepStatuses.length}
                                onNavigate={navigateStep}
                            />
                            <SimulationHistory
                                data={simulationData.stepStatuses}
                                currentStep={currentStep}
                                onSelectStep={navigateStep}
                            />
                        </>
                    )}
                </div>
            </div>
        </div>
    );
}

export default App;