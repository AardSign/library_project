import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import {
  TextField,
  Button,
  Typography,
  Container,
  Box,
  Paper,
  MenuItem,
} from "@mui/material";
import InputMask from "react-input-mask";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function RegisterBook() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    codigoBarras: "",
    nome: "",
    autor: "",
    edicao: "",
    categoria: "",
    quantidade: "",
    disponibilidade: "",
  });
  const [errorMessage, setErrorMessage] = useState("");

  // Verificação do tipo de usuário (apenas administradores)
  useEffect(() => {
    const storedUserType = localStorage.getItem("userType");
    if (!storedUserType) {
      alert("Você precisa estar autenticado para registrar livros.");
      navigate("/main-page");
    }
  }, [navigate]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleRegister = async () => {
    try {
      const data = {
        ...formData,
        codigoBarras: formData.codigoBarras.replace(/\D/g, ""), // Remove caracteres não numéricos do código de barras
      };

      const username = localStorage.getItem("username");
      const password = localStorage.getItem("password");
      const basicAuth = btoa(`${username}:${password}`);

      // Enviar os dados como JSON
      const response = await axios.post(
        "http://localhost:8080/api/livros",
        data,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Basic ${basicAuth}`,
          },
        }
      );

      if (response.status === 201) {
        toast.success("Livro registrado com sucesso!", {
          position: "top-center",
          autoClose: 3000,
        });

  
      }
    } catch (error) {
      console.error("Erro ao registrar livro:", error);
      setErrorMessage(
        error.response?.data?.message || "Erro ao registrar livro."
      );
      toast.error("Erro ao registrar livro.", {
        position: "top-center",
        autoClose: 5000,
      });
    }
  };

  return (
    <Box
      sx={{
        background: "linear-gradient(135deg, #e0e0e0, #ffffff)",
        height: "100vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        padding: 2,
      }}
    >
      <Paper
        elevation={4}
        sx={{
          padding: 4,
          width: "100%",
          maxWidth: 600,
          borderRadius: 3,
          boxShadow: "0px 8px 16px rgba(0, 0, 0, 0.2)",
        }}
      >
        <Container>
          <Typography
            variant="h5"
            gutterBottom
            align="center"
            sx={{ fontWeight: "bold", color: "#333" }}
          >
            Registro de Livro
          </Typography>
          {errorMessage && (
            <Typography
              variant="body2"
              color="error"
              align="center"
              sx={{ marginBottom: 2 }}
            >
              {errorMessage}
            </Typography>
          )}
          <InputMask
            mask="9999-9999-9999"
            value={formData.codigoBarras}
            onChange={(e) =>
              setFormData((prevData) => ({
                ...prevData,
                codigoBarras: e.target.value,
              }))
            }
          >
            {(inputProps) => (
              <TextField
                {...inputProps}
                label="Código de Barras"
                name="codigoBarras"
                variant="outlined"
                fullWidth
                margin="normal"
              />
            )}
          </InputMask>
          <TextField
            label="Título"
            name="nome"
            variant="outlined"
            fullWidth
            margin="normal"
            value={formData.nome}
            onChange={handleInputChange}
          />
          <TextField
            label="Autor"
            name="autor"
            variant="outlined"
            fullWidth
            margin="normal"
            value={formData.autor}
            onChange={handleInputChange}
          />
          <TextField
            label="Edição"
            name="edicao"
            variant="outlined"
            fullWidth
            margin="normal"
            value={formData.edicao}
            onChange={handleInputChange}
          />
          <TextField
            select
            label="Gênero"
            name="categoria"
            variant="outlined"
            fullWidth
            margin="normal"
            value={formData.categoria}
            onChange={handleInputChange}
          >
            <MenuItem value="Ficção Científica">Ficção Científica</MenuItem>
  <MenuItem value="Romance">Romance</MenuItem>
  <MenuItem value="Fantasia">Fantasia</MenuItem>
  <MenuItem value="Aventura">Aventura</MenuItem>
  <MenuItem value="Terror">Terror</MenuItem>
  <MenuItem value="Suspense">Suspense</MenuItem>
  <MenuItem value="Mistério">Mistério</MenuItem>
  <MenuItem value="Drama">Drama</MenuItem>
  <MenuItem value="Biografia">Biografia</MenuItem>
  <MenuItem value="Autobiografia">Autobiografia</MenuItem>
  <MenuItem value="História">História</MenuItem>
  <MenuItem value="História em Quadrinhos">História em Quadrinhos</MenuItem>
  <MenuItem value="Poesia">Poesia</MenuItem>
  <MenuItem value="Ensaios">Ensaios</MenuItem>
  <MenuItem value="Literatura Clássica">Literatura Clássica</MenuItem>
  <MenuItem value="Literatura Contemporânea">Literatura Contemporânea</MenuItem>
  <MenuItem value="Policial">Policial</MenuItem>
  <MenuItem value="Jovem Adulto">Jovem Adulto</MenuItem>
  <MenuItem value="Infantil">Infantil</MenuItem>
  <MenuItem value="Didático">Didático</MenuItem>
  <MenuItem value="Autoajuda">Autoajuda</MenuItem>
  <MenuItem value="Psicologia">Psicologia</MenuItem>
  <MenuItem value="Filosofia">Filosofia</MenuItem>
  <MenuItem value="Religioso">Religioso</MenuItem>
  <MenuItem value="Espiritualidade">Espiritualidade</MenuItem>
  <MenuItem value="Humor">Humor</MenuItem>
  <MenuItem value="Negócios">Negócios</MenuItem>
  <MenuItem value="Tecnologia">Tecnologia</MenuItem>
  <MenuItem value="Ciência">Ciência</MenuItem>
  <MenuItem value="Culinária">Culinária</MenuItem>
  <MenuItem value="Viagem">Viagem</MenuItem>
  <MenuItem value="Distopia">Distopia</MenuItem>
  <MenuItem value="Realismo Mágico">Realismo Mágico</MenuItem>
  <MenuItem value="Épico">Épico</MenuItem>
  <MenuItem value="Erótico">Erótico</MenuItem>
</TextField>
          <TextField
            select
            label="Quantidade"
            name="quantidade"
            variant="outlined"
            fullWidth
            margin="normal"
            value={formData.quantidade}
            onChange={handleInputChange}
          >
            {[...Array(101).keys()].map((n) => (
              <MenuItem key={n} value={n}>
                {n}
              </MenuItem>
            ))}
          </TextField>
      
          <Button
            variant="contained"
            fullWidth
            onClick={handleRegister}
            sx={{
              marginTop: 2,
              backgroundColor: "#333",
              color: "#fff",
              "&:hover": {
                backgroundColor: "#555",
              },
            }}
          >
            Registrar Livro
          </Button>
        </Container>
      </Paper>
      <ToastContainer />
    </Box>
  );
}

export default RegisterBook;
