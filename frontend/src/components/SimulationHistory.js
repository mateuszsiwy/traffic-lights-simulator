import React from 'react';
import './SimulationHistory.css';

const SimulationHistory = ({ simulationData, currentStep, onStepClick }) => {
  if (!simulationData || !simulationData.stepStatuses) {
    return <div className="empty-history">Brak historii symulacji</div>;
  }

  return (
    <div className="simulation-history">
      <h3>Historia symulacji</h3>
      <div className="step-list">
        {simulationData.stepStatuses.map((step, index) => (
          <div
            key={index}
            className={`step-item ${currentStep === index ? 'active' : ''}`}
            onClick={() => onStepClick(index)}
          >
            <div className="step-header">
              <span className="step-number">Krok {index + 1}</span>
            </div>
            <div className="step-details">
              <div className="vehicles-section">
                <strong>Pojazdy opuszczające:</strong>
                {step.leftVehicles && step.leftVehicles.length > 0
                  ? step.leftVehicles.join(', ')
                  : 'Brak'}
              </div>
              <div className="pedestrians-section">
                <strong>Piesi przechodzący:</strong>
                {step.crossedPedestrians && step.crossedPedestrians.length > 0
                  ? step.crossedPedestrians.join(', ')
                  : 'Brak'}
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default SimulationHistory;