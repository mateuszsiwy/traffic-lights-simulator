import React from 'react';
import './SimulationControls.css';

const SimulationControls = ({ currentStep, totalSteps, onNavigate }) => {
  return (
    <div className="simulation-controls">
      <h3>Kontrola symulacji</h3>
      <div className="controls-buttons">
        <button
          onClick={() => onNavigate(0)}
          disabled={currentStep === 0}
        >
          &lt;&lt; Początek
        </button>
        <button
          onClick={() => onNavigate(currentStep - 1)}
          disabled={currentStep === 0}
        >
          &lt; Wstecz
        </button>
        <span className="step-counter">Krok {currentStep + 1} / {totalSteps}</span>
        <button
          onClick={() => onNavigate(currentStep + 1)}
          disabled={currentStep === totalSteps - 1}
        >
          Dalej &gt;
        </button>
        <button
          onClick={() => onNavigate(totalSteps - 1)}
          disabled={currentStep === totalSteps - 1}
        >
          Koniec &gt;&gt;
        </button>
      </div>

      <div className="step-slider">
        <input
          type="range"
          min="0"
          max={totalSteps - 1}
          value={currentStep}
          onChange={(e) => onNavigate(parseInt(e.target.value))}
        />
      </div>
    </div>
  );
};

export default SimulationControls;