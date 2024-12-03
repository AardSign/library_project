import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { TextField, Button, Box, Typography, Paper } from "@mui/material";

const Login = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    cpf: "",
    senha: "",
  });
  const [errorMessage, setErrorMessage] = useState("");

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleLogin = async () => {
    try {
      const response = await axios.post("http://localhost:8080/api/auth/login", formData);

      // Armazenar dados do usuário no localStorage
      localStorage.setItem("userName", response.data.nome); // Corrigido: Garantir que 'nome' vem da API corretamente.
      localStorage.setItem("userType", response.data.tipo);
      localStorage.setItem("username", formData.cpf);
      localStorage.setItem("password", formData.senha);

      // Redirecionar para a página principal
      navigate("/main-page");
    } catch (error) {
      console.error("Erro ao fazer login:", error);
      setErrorMessage(
        error.response?.data?.message || "Credenciais inválidas. Tente novamente."
      );
    }
  };

  return (
    <Box
      sx={{
        background: "linear-gradient(135deg, #f5f7fa, #c3cfe2)",
        height: "100vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        padding: 2,
      }}
    >
      <Paper
        elevation={6}
        sx={{
          padding: 5,
          width: "100%",
          maxWidth: 400,
          borderRadius: 4,
          boxShadow: "0px 8px 24px rgba(0, 0, 0, 0.3)",
        }}
      >
        <Typography
          variant="h4"
          gutterBottom
          align="center"
          sx={{ fontWeight: "bold", color: "#444" }}
        >
          Bem-vindo à Biblioteca
        </Typography>
        <Typography
          variant="body1"
          align="center"
          sx={{ marginBottom: 3, color: "#666" }}
        >
          Faça login para acessar o sistema.
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
          label="CPF"
          name="cpf"
          variant="outlined"
          fullWidth
          margin="normal"
          value={formData.cpf}
          onChange={handleInputChange}
          sx={{
            "& .MuiOutlinedInput-root": {
              borderRadius: 3,
              backgroundColor: "#fff",
            },
          }}
        />
        <TextField
          label="Senha"
          name="senha"
          type="password"
          variant="outlined"
          fullWidth
          margin="normal"
          value={formData.senha}
          onChange={handleInputChange}
          sx={{
            "& .MuiOutlinedInput-root": {
              borderRadius: 3,
              backgroundColor: "#fff",
            },
          }}
        />
        <Button
          variant="contained"
          fullWidth
          onClick={handleLogin}
          sx={{
            marginTop: 3,
            backgroundColor: "#007BFF",
            color: "#fff",
            padding: 1.5,
            fontWeight: "bold",
            fontSize: "1rem",
            borderRadius: 3,
            "&:hover": {
              backgroundColor: "#0056b3",
            },
          }}
        >
          Entrar
        </Button>
        <Typography
          variant="body2"
          align="center"
          sx={{ marginTop: 3, color: "#777" }}
        >
          © 2024 Biblioteca Virtual
        </Typography>
      </Paper>
    </Box>
  );
};

export default Login;
