import React, {useState} from 'react';
import axios from 'axios';
import './App.css';

function App() {
    const [file, setFile] = useState(null);
    const [output, setOutput] = useState(null);

    const handleFileChange = (event) => {
        setFile(event.target.files[0]);
    };

    const handleUpload = async () => {
        const formData = new FormData();
        formData.append('file', file);

        try {
            const response = await axios.post('http://localhost:8080/api/simulation/upload', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
            setOutput(response.data);
        } catch (error) {
            console.error('Error uploading file:', error);
        }
    };

    return (
        <div className="App">
            <h1>Traffic Lights Simulator</h1>
            <input type="file" onChange={handleFileChange}/>
            <button onClick={handleUpload}>Upload</button>
            {output && (
                <div>
                    <h2>Output:</h2>
                    <pre>{JSON.stringify(output, null, 2)}</pre>
                </div>
            )}
        </div>
    );
}

export default App;