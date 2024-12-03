import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { TextField, Button, Typography, Container, Box, Paper, MenuItem } from "@mui/material";
import InputMask from "react-input-mask";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function RegisterClient() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    cpf: "",
    telefone: "",
    endereco: "",
    nome: "",
    dataNascimento: "",
    preferencias: "",
  });
  const [errorMessage, setErrorMessage] = useState("");

  // Verificação do tipo de usuário
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
        cpf: formData.cpf.replace(/\D/g, ""), // Remove pontos e hífen do CPF
      };
  
      const username = localStorage.getItem("username");
      const password = localStorage.getItem("password");
      const basicAuth = btoa(`${username}:${password}`);
  
      const response = await axios.post("http://localhost:8080/api/clientes", data, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Basic ${basicAuth}`,
        },
      });
  
      if (response.status === 200) {
        toast.success("Cliente registrado com sucesso!");
  
      }
    } catch (error) {
      console.error("Erro ao registrar cliente:", error);
      setErrorMessage(error.response?.data?.message || "Erro ao registrar cliente.");
      toast.error("Erro ao registrar cliente.");
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
            Registro de Cliente
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
          <InputMask
            mask="999.999.999-99"
            value={formData.cpf}
            onChange={(e) =>
              setFormData((prevData) => ({
                ...prevData,
                cpf: e.target.value,
              }))
            }
          >
            {(inputProps) => (
              <TextField
                {...inputProps}
                label="CPF"
                name="cpf"
                variant="outlined"
                fullWidth
                margin="normal"
              />
            )}
          </InputMask>
          <InputMask
            mask="(99) 99999-9999"
            value={formData.telefone}
            onChange={(e) =>
              setFormData((prevData) => ({
                ...prevData,
                telefone: e.target.value,
              }))
            }
          >
            {(inputProps) => (
              <TextField
                {...inputProps}
                label="Telefone"
                name="telefone"
                variant="outlined"
                fullWidth
                margin="normal"
              />
            )}
          </InputMask>
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
  select
  label="Preferências"
  name="preferencias"
  variant="outlined"
  fullWidth
  margin="normal"
  value={formData.preferencias}
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
            Registrar Cliente
          </Button>
        </Container>
      </Paper>
      <ToastContainer />
    </Box>
  );
}

export default RegisterClient;
