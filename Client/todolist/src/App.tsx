import { Box, TextField } from '@mui/material'
import './App.css'
import { useEffect, useState } from 'react'

function App() {
  const [todos, setTodos] = useState();

  useEffect(() => {
    const fetchData = async () => {
      try{
        const response = await fetch('http://localhost:8080/task/2')
        if(!response.ok) {
          throw new Error('Resposta diferente de Ok ')
        }
        const resultado = await response.json()
        console.log("AQUI -> ",resultado)
        setTodos(resultado)
      } catch(error) {
        console.error("Erro buscando dados", error)
      }
    };

    fetchData();
  }, [])

  return (
    <div>
      <h1>Tasks</h1>
      {todos ? (
        <div>{todos.description}</div>
      ) : (
        <p>Loading...</p>
      )}
    </div>
  )
}

export default App
