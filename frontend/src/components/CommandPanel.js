import React, { useState } from 'react';
import './CommandPanel.css';

const CommandPanel = ({ commands, addCommand, runSimulation, handleFileUpload }) => {
  const [commandType, setCommandType] = useState('addVehicle');
  const [vehicleId, setVehicleId] = useState('');
  const [startRoad, setStartRoad] = useState('north');
  const [endRoad, setEndRoad] = useState('south');
  const [pedestrianId, setPedestrianId] = useState('');
  const [origin, setOrigin] = useState('north_east');
  const [destination, setDestination] = useState('south_east');

  const handleAddCommand = () => {
    if (commandType === 'addVehicle') {
      if (!vehicleId) return alert('Podaj ID pojazdu');

      addCommand({
        type: 'addVehicle',
        vehicleId,
        startRoad,
        endRoad
      });

      setVehicleId('');
    } else if (commandType === 'addPedestrian') {
      if (!pedestrianId) return alert('Podaj ID pieszego');

      addCommand({
        type: 'addPedestrian',
        pedestrianId,
        origin,
        destination
      });

      setPedestrianId('');
    } else if (commandType === 'step') {
      addCommand({ type: 'step' });
    }
  };

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      handleFileUpload(file);
    }
  };

  return (
    <div className="command-panel">
      <h3>Panel komend</h3>

      <div className="command-form">
        <div className="command-type">
          <label>Typ komendy:</label>
          <select value={commandType} onChange={e => setCommandType(e.target.value)}>
            <option value="addVehicle">Dodaj pojazd</option>
            <option value="addPedestrian">Dodaj pieszego</option>
            <option value="step">Krok symulacji</option>
          </select>
        </div>

        {commandType === 'addVehicle' && (
          <div className="vehicle-form">
            <div>
              <label>ID pojazdu:</label>
              <input
                type="text"
                value={vehicleId}
                onChange={e => setVehicleId(e.target.value)}
              />
            </div>
            <div>
              <label>Droga początkowa:</label>
              <select value={startRoad} onChange={e => setStartRoad(e.target.value)}>
                <option value="north">Północ</option>
                <option value="south">Południe</option>
                <option value="east">Wschód</option>
                <option value="west">Zachód</option>
              </select>
            </div>
            <div>
              <label>Droga końcowa:</label>
              <select value={endRoad} onChange={e => setEndRoad(e.target.value)}>
                <option value="north">Północ</option>
                <option value="south">Południe</option>
                <option value="east">Wschód</option>
                <option value="west">Zachód</option>
              </select>
            </div>
          </div>
        )}

        {commandType === 'addPedestrian' && (
          <div className="pedestrian-form">
            <div>
              <label>ID pieszego:</label>
              <input
                type="text"
                value={pedestrianId}
                onChange={e => setPedestrianId(e.target.value)}
              />
            </div>
            <div>
              <label>Początek:</label>
              <select value={origin} onChange={e => setOrigin(e.target.value)}>
                <option value="north_east">Północny-Wschód</option>
                <option value="north_west">Północny-Zachód</option>
                <option value="south_east">Południowy-Wschód</option>
                <option value="south_west">Południowy-Zachód</option>
              </select>
            </div>
            <div>
              <label>Cel:</label>
              <select value={destination} onChange={e => setDestination(e.target.value)}>
                <option value="north_east">Północny-Wschód</option>
                <option value="north_west">Północny-Zachód</option>
                <option value="south_east">Południowy-Wschód</option>
                <option value="south_west">Południowy-Zachód</option>
              </select>
            </div>
          </div>
        )}

        <button onClick={handleAddCommand}>Dodaj komendę</button>
      </div>

      <div className="command-list">
        <h4>Lista komend ({commands.length})</h4>
        <div className="commands-container">
          {commands.map((cmd, index) => (
            <div key={index} className="command-item">
              {cmd.type === 'addVehicle' && (
                <span>Dodaj pojazd: {cmd.vehicleId} ({cmd.startRoad}→{cmd.endRoad})</span>
              )}
              {cmd.type === 'addPedestrian' && (
                <span>Dodaj pieszego: {cmd.pedestrianId} ({cmd.origin}→{cmd.destination})</span>
              )}
              {cmd.type === 'step' && <span>Krok symulacji</span>}
            </div>
          ))}
        </div>
      </div>

      <div className="run-controls">
        <button onClick={runSimulation} disabled={commands.length === 0}>
          Uruchom symulację
        </button>

        <div className="file-upload">
          <label>Wczytaj z pliku JSON:</label>
          <input type="file" accept=".json" onChange={handleFileChange} />
        </div>
      </div>
    </div>
  );
};

export default CommandPanel;