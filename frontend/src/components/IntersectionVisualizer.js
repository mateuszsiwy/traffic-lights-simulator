import React, {useEffect, useRef} from 'react';
import './IntersectionVisualizer.css';

const IntersectionVisualizer = ({data, step}) => {
    const canvasRef = useRef(null);

    useEffect(() => {
        if (!canvasRef.current || !data) return;

        const canvas = canvasRef.current;
        const ctx = canvas.getContext('2d');
        const width = canvas.width;
        const height = canvas.height;

        // Wyczyść canvas
        ctx.clearRect(0, 0, width, height);

        // Narysuj skrzyżowanie
        drawIntersection(ctx, width, height, data);

    }, [data, step]);

    const drawIntersection = (ctx, width, height, data) => {
        const centerX = width / 2;
        const centerY = height / 2;
        const roadWidth = 60;
        const crosswalkWidth = 40;

        // Narysuj tło
        ctx.fillStyle = '#8aba87'; // Kolor trawy
        ctx.fillRect(0, 0, width, height);

        // Narysuj drogi
        ctx.fillStyle = '#555555'; // Kolor asfaltu

        // Droga pozioma
        ctx.fillRect(0, centerY - roadWidth / 2, width, roadWidth);

        // Droga pionowa
        ctx.fillRect(centerX - roadWidth / 2, 0, roadWidth, height);

        // Pasy drogowe
        ctx.setLineDash([15, 10]);
        ctx.strokeStyle = 'white';
        ctx.lineWidth = 3;

        // Pas na drodze poziomej
        ctx.beginPath();
        ctx.moveTo(0, centerY);
        ctx.lineTo(width, centerY);
        ctx.stroke();

        // Pas na drodze pionowej
        ctx.beginPath();
        ctx.moveTo(centerX, 0);
        ctx.lineTo(centerX, height);
        ctx.stroke();

        ctx.setLineDash([]);

        // Narysuj przejścia dla pieszych
        drawCrosswalk(ctx, centerX - roadWidth / 2 - crosswalkWidth, centerY - roadWidth / 2, crosswalkWidth, roadWidth, 'west');
        drawCrosswalk(ctx, centerX + roadWidth / 2, centerY - roadWidth / 2, crosswalkWidth, roadWidth, 'east');
        drawCrosswalk(ctx, centerX - roadWidth / 2, centerY - roadWidth / 2 - crosswalkWidth, roadWidth, crosswalkWidth, 'north');
        drawCrosswalk(ctx, centerX - roadWidth / 2, centerY + roadWidth / 2, roadWidth, crosswalkWidth, 'south');

        // Rysuj światła
        drawTrafficLights(ctx, centerX, centerY, roadWidth);

        // Rysuj pojazdy (przykład)
        drawVehicles(ctx, centerX, centerY, roadWidth, data);

        // Rysuj pieszych (przykład)
        drawPedestrians(ctx, centerX, centerY, roadWidth, crosswalkWidth, data);

        // Dodaj legendę
        drawLegend(ctx, width, height);
    };

    const drawCrosswalk = (ctx, x, y, width, height, direction) => {
        const stripeWidth = 6;
        const gap = 10;

        ctx.fillStyle = 'white';

        if (direction === 'north' || direction === 'south') {
            // Poziome pasy przejścia
            for (let i = 0; i < height; i += stripeWidth + gap) {
                ctx.fillRect(x, y + i, width, stripeWidth);
            }
        } else {
            // Pionowe pasy przejścia
            for (let i = 0; i < width; i += stripeWidth + gap) {
                ctx.fillRect(x + i, y, stripeWidth, height);
            }
        }
    };

    const drawTrafficLights = (ctx, centerX, centerY, roadWidth) => {
        const lightSize = 8;
        const lightSpacing = 12;
        const boxWidth = 10;
        const boxHeight = 40;
        const distFromRoad = 15;

        // Funkcja pomocnicza do rysowania pojedynczej sygnalizacji
        const drawLight = (x, y, orientation) => {
            // Rysuj pudełko sygnalizacji
            ctx.fillStyle = '#333';

            if (orientation === 'vertical') {
                ctx.fillRect(x - boxWidth / 2, y - boxHeight / 2, boxWidth, boxHeight);

                // Czerwone światło
                ctx.fillStyle = '#ff0000';
                let redLight = {x: x, y: y - lightSpacing};
                ctx.beginPath();
                ctx.arc(redLight.x, redLight.y, lightSize, 0, Math.PI * 2);
                ctx.fill();

                // Żółte światło
                ctx.fillStyle = '#ffff00';
                let yellowLight = {x: x, y: y};
                ctx.beginPath();
                ctx.arc(yellowLight.x, yellowLight.y, lightSize, 0, Math.PI * 2);
                ctx.fill();

                // Zielone światło
                ctx.fillStyle = '#00ff00';
                let greenLight = {x: x, y: y + lightSpacing};
                ctx.beginPath();
                ctx.arc(greenLight.x, greenLight.y, lightSize, 0, Math.PI * 2);
                ctx.fill();
            } else {
                ctx.fillRect(x - boxHeight / 2, y - boxWidth / 2, boxHeight, boxWidth);

                // Czerwone światło
                ctx.fillStyle = '#ff0000';
                let redLight = {x: x - lightSpacing, y: y};
                ctx.beginPath();
                ctx.arc(redLight.x, redLight.y, lightSize, 0, Math.PI * 2);
                ctx.fill();

                // Żółte światło
                ctx.fillStyle = '#ffff00';
                let yellowLight = {x: x, y: y};
                ctx.beginPath();
                ctx.arc(yellowLight.x, yellowLight.y, lightSize, 0, Math.PI * 2);
                ctx.fill();

                // Zielone światło
                ctx.fillStyle = '#00ff00';
                let greenLight = {x: x + lightSpacing, y: y};
                ctx.beginPath();
                ctx.arc(greenLight.x, greenLight.y, lightSize, 0, Math.PI * 2);
                ctx.fill();
            }
        };

        // Narysuj cztery sygnalizacje
        // Północna
        drawLight(centerX - roadWidth / 4, centerY - roadWidth / 2 - distFromRoad, 'horizontal');

        // Południowa
        drawLight(centerX + roadWidth / 4, centerY + roadWidth / 2 + distFromRoad, 'horizontal');

        // Wschodnia
        drawLight(centerX + roadWidth / 2 + distFromRoad, centerY - roadWidth / 4, 'vertical');

        // Zachodnia
        drawLight(centerX - roadWidth / 2 - distFromRoad, centerY + roadWidth / 4, 'vertical');

        // Sygnalizacje dla pieszych
        drawPedestrianLights(ctx, centerX, centerY, roadWidth);
    };

    const drawPedestrianLights = (ctx, centerX, centerY, roadWidth) => {
        const pedLightSize = 8;
        const distFromRoad = 10;
        const cornerOffset = 70;

        // Funkcja pomocnicza do rysowania pojedynczej sygnalizacji dla pieszych
        const drawPedLight = (x, y) => {
            // Pudełko sygnalizacji
            ctx.fillStyle = '#444';
            ctx.fillRect(x - 6, y - 15, 12, 30);

            // Czerwone światło
            ctx.fillStyle = '#ff0000';
            ctx.beginPath();
            ctx.arc(x, y - 8, pedLightSize, 0, Math.PI * 2);
            ctx.fill();

            // Zielone światło
            ctx.fillStyle = '#00ff00';
            ctx.beginPath();
            ctx.arc(x, y + 8, pedLightSize, 0, Math.PI * 2);
            ctx.fill();
        };

        // Północno-zachodni róg
        drawPedLight(centerX - cornerOffset, centerY - cornerOffset);

        // Północno-wschodni róg
        drawPedLight(centerX + cornerOffset, centerY - cornerOffset);

        // Południowo-wschodni róg
        drawPedLight(centerX + cornerOffset, centerY + cornerOffset);

        // Południowo-zachodni róg
        drawPedLight(centerX - cornerOffset, centerY + cornerOffset);
    };

    const drawVehicles = (ctx, centerX, centerY, roadWidth, data) => {
        // Przykładowe pojazdy
        // W rzeczywistej implementacji ta funkcja powinna używać danych z props
        const carSize = 20;

        // Funkcja do rysowania pojedynczego pojazdu
        const drawVehicle = (x, y, color) => {
            ctx.fillStyle = color;
            ctx.fillRect(x - carSize / 2, y - carSize / 2, carSize, carSize);

            // Dodaj szczegóły pojazdu
            ctx.fillStyle = '#333';
            ctx.fillRect(x - carSize / 3, y - carSize / 3, 2 * carSize / 3, 2 * carSize / 3);
        };

        // Dodaj pojazdy na każdej drodze
        // Przykładowe pojazdy - w prawdziwej implementacji użyj danych z data.leftVehicles

        // Pojazd z północy
        drawVehicle(centerX, centerY - roadWidth, '#ff0000');

        // Pojazd ze wschodu
        drawVehicle(centerX + roadWidth, centerY, '#0000ff');

        // Pojazd z południa
        drawVehicle(centerX, centerY + roadWidth, '#00ff00');

        // Pojazd z zachodu
        drawVehicle(centerX - roadWidth, centerY, '#ffff00');
    };

    const drawPedestrians = (ctx, centerX, centerY, roadWidth, crosswalkWidth, data) => {
        const pedSize = 8;

        // Funkcja do rysowania pojedynczego pieszego
        const drawPedestrian = (x, y) => {
            ctx.fillStyle = '#333333';
            ctx.beginPath();
            ctx.arc(x, y, pedSize, 0, Math.PI * 2);
            ctx.fill();

            // Dodaj szczegóły pieszego (np. głowa)
            ctx.beginPath();
            ctx.arc(x, y - 3, pedSize / 2, 0, Math.PI * 2);
            ctx.fill();
        };

        // Dodaj pieszych na przejściach (przykładowo)
        // W prawdziwej implementacji użyj danych z data.crossedPedestrians

        // Pieszy na północnym przejściu
        drawPedestrian(centerX - 15, centerY - roadWidth / 2 - crosswalkWidth / 2);

        // Pieszy na wschodnim przejściu
        drawPedestrian(centerX + roadWidth / 2 + crosswalkWidth / 2, centerY + 10);

        // Pieszy na południowym przejściu
        drawPedestrian(centerX + 20, centerY + roadWidth / 2 + crosswalkWidth / 2);

        // Pieszy na zachodnim przejściu
        drawPedestrian(centerX - roadWidth / 2 - crosswalkWidth / 2, centerY - 15);
    };

    const drawLegend = (ctx, width, height) => {
        const legendX = width - 180;
        const legendY = height - 120;
        const lineHeight = 20;

        ctx.fillStyle = 'rgba(255, 255, 255, 0.8)';
        ctx.fillRect(legendX - 10, legendY - 25, 170, 120);
        ctx.strokeStyle = '#333';
        ctx.strokeRect(legendX - 10, legendY - 25, 170, 120);

        ctx.fillStyle = '#333';
        ctx.font = 'bold 14px Arial';
        ctx.fillText('Legenda:', legendX, legendY - 5);

        ctx.font = '12px Arial';

        // Pojazdy
        ctx.fillStyle = '#ff0000';
        ctx.fillRect(legendX, legendY + 5, 15, 15);
        ctx.fillStyle = '#333';
        ctx.fillText('Pojazd', legendX + 25, legendY + 17);

        // Piesi
        ctx.fillStyle = '#333';
        ctx.beginPath();
        ctx.arc(legendX + 8, legendY + 35, 8, 0, Math.PI * 2);
        ctx.fill();
        ctx.fillText('Pieszy', legendX + 25, legendY + 37);

        // Światła
        ctx.fillStyle = '#ff0000';
        ctx.beginPath();
        ctx.arc(legendX + 8, legendY + 55, 8, 0, Math.PI * 2);
        ctx.fill();
        ctx.fillStyle = '#333';
        ctx.fillText('Światło czerwone', legendX + 25, legendY + 57);

        ctx.fillStyle = '#00ff00';
        ctx.beginPath();
        ctx.arc(legendX + 8, legendY + 75, 8, 0, Math.PI * 2);
        ctx.fill();
        ctx.fillStyle = '#333';
        ctx.fillText('Światło zielone', legendX + 25, legendY + 77);
    };

    return (
        <div className="intersection-visualizer">
            <h2>Skrzyżowanie - Krok {step + 1}</h2>
            <canvas ref={canvasRef} className="intersection-canvas" width={600} height={600}/>
            {data && (
                <div className="step-info">
                    <p>
                        <strong>Pojazdy, które opuściły skrzyżowanie:</strong> {' '}
                        {data.leftVehicles && data.leftVehicles.length > 0
                            ? data.leftVehicles.join(', ')
                            : 'Brak'}
                    </p>
                    <p>
                        <strong>Piesi, którzy przeszli przez przejście:</strong> {' '}
                        {data.crossedPedestrians && data.crossedPedestrians.length > 0
                            ? data.crossedPedestrians.join(', ')
                            : 'Brak'}
                    </p>
                </div>
            )}
        </div>
    );
};

export default IntersectionVisualizer;