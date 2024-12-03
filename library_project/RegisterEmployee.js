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

function RegisterEmployee() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    cpf: "",
    telefone: "",
    endereco: "",
    nome: "",
    dataNascimento: "",
    funcao: "",
    tipo: "",
    senha: "",
  });
  const [errorMessage, setErrorMessage] = useState("");
  const [isAdmin, setIsAdmin] = useState(true); // Controle de permissão

  // Verificação do tipo de usuário
  useEffect(() => {
    const storedUserType = localStorage.getItem("userType");
    if (!storedUserType || storedUserType !== "administrador") {
      setIsAdmin(false);
    }
  }, []);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleRegister = async () => {
    try {
      const username = localStorage.getItem("username");
      const password = localStorage.getItem("password");
      const basicAuth = btoa(`${username}:${password}`);

      const response = await axios.post(
        "http://localhost:8080/api/funcionarios",
        formData,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Basic ${basicAuth}`,
          },
        }
      );

      if (response.status === 201) {
        alert("Funcionário registrado com sucesso!");
        navigate("/dashboard");
      }
    } catch (error) {
      console.error("Erro ao registrar funcionário:", error);
      setErrorMessage(
        error.response?.data?.message || "Erro ao registrar funcionário."
      );
    }
  };

  if (!isAdmin) {
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
            textAlign: "center",
          }}
        >
          <Typography variant="h6" color="error">
            Acesso negado. Apenas administradores podem acessar esta página.
          </Typography>
        </Paper>
      </Box>
    );
  }

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
            Registro de Funcionário
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
          <TextField
            label="Nome"
            name="nome"
            variant="outlined"
            fullWidth
            margin="normal"
            value={formData.nome}
            onChange={handleInputChange}
          />
          <TextField
            label="CPF"
            name="cpf"
            variant="outlined"
            fullWidth
            margin="normal"
            value={formData.cpf}
            onChange={handleInputChange}
          />
          <TextField
            label="Telefone"
            name="telefone"
            variant="outlined"
            fullWidth
            margin="normal"
            value={formData.telefone}
            onChange={handleInputChange}
          />
          <TextField
            label="Endereço"
            name="endereco"
            variant="outlined"
            fullWidth
            margin="normal"
            value={formData.endereco}
            onChange={handleInputChange}
          />
          <TextField
            label="Data de Nascimento"
            name="dataNascimento"
            type="date"
            variant="outlined"
            fullWidth
            margin="normal"
            value={formData.dataNascimento}
            onChange={handleInputChange}
            InputLabelProps={{ shrink: true }}
          />
          <TextField
            label="Função"
            name="funcao"
            variant="outlined"
            fullWidth
            margin="normal"
            value={formData.funcao}
            onChange={handleInputChange}
          />
          <TextField
            select
            label="Tipo"
            name="tipo"
            variant="outlined"
            fullWidth
            margin="normal"
            value={formData.tipo}
            onChange={handleInputChange}
          >
            <MenuItem value="administrador">Administrador</MenuItem>
            <MenuItem value="comum">Funcionário Comum</MenuItem>
          </TextField>
          <TextField
            label="Senha"
            name="senha"
            type="password"
            variant="outlined"
            fullWidth
            margin="normal"
            value={formData.senha}
            onChange={handleInputChange}
          />
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
            Registrar Funcionário
          </Button>
        </Container>
      </Paper>
    </Box>
  );
}

export default RegisterEmployee;
