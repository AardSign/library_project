import React, { useState, useEffect } from "react";
import axios from "axios";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Button,
  Typography,
  Modal,
  TextField,
  Select,
  MenuItem,
  Box,
  Snackbar,
  Alert,
} from "@mui/material";

const LoanManagement = () => {
  const [loans, setLoans] = useState([]);
  const [clients, setClients] = useState([]);
  const [books, setBooks] = useState([]);
  const [open, setOpen] = useState(false);
  const [notification, setNotification] = useState({ open: false, message: "", severity: "" });
  const [editingLoan, setEditingLoan] = useState(null);
  const [formData, setFormData] = useState({
    clienteId: "",
    livroId: "",
    dataEmprestimo: "",
    dataPrevista: "",
    status: "ativo",
  });

  const [search, setSearch] = useState("");

  useEffect(() => {
    fetchLoans();
    fetchClients();
    fetchBooks();
  }, []);

  const fetchLoans = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/emprestimos");
      setLoans(response.data);
    } catch (error) {
      console.error("Erro ao buscar empréstimos:", error);
    }
  };

  const fetchClients = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/clientes");
      setClients(response.data);
    } catch (error) {
      console.error("Erro ao buscar clientes:", error);
    }
  };

  const fetchBooks = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/livros");
      setBooks(response.data);
    } catch (error) {
      console.error("Erro ao buscar livros:", error);
    }
  };

  const handleOpen = (loan) => {
    setEditingLoan(loan || null);
    setFormData(
      loan || {
        clienteId: "",
        livroId: "",
        dataEmprestimo: "",
        dataPrevista: "",
        status: "ativo",
      }
    );
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    setEditingLoan(null);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSave = async () => {
    if (!formData.clienteId || !formData.livroId) {
      setNotification({ open: true, message: "Cliente e Livro são obrigatórios.", severity: "error" });
      return;
    }

    try {
      if (editingLoan) {
        await axios.put(`http://localhost:8080/api/emprestimos/${editingLoan.id}`, formData);
      } else {
        await axios.post("http://localhost:8080/api/emprestimos", formData);
      }
      fetchLoans();
      handleClose();
      setNotification({ open: true, message: "Empréstimo salvo com sucesso.", severity: "success" });
    } catch (error) {
      console.error("Erro ao salvar empréstimo:", error);
      setNotification({ open: true, message: "Erro ao salvar empréstimo.", severity: "error" });
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm("Tem certeza que deseja deletar este empréstimo?")) {
      try {
        await axios.delete(`http://localhost:8080/api/emprestimos/${id}`);
        fetchLoans();
        setNotification({ open: true, message: "Empréstimo deletado com sucesso.", severity: "success" });
      } catch (error) {
        console.error("Erro ao deletar empréstimo:", error);
        setNotification({ open: true, message: "Erro ao deletar empréstimo.", severity: "error" });
      }
    }
  };

  const filteredLoans = loans.filter((loan) => {
    const clienteNome = clients.find((client) => client.id === loan.clienteId)?.nome;
    const livroTitulo = books.find((book) => book.id === loan.livroId)?.nome;
    const status = loan.status || "";

    return (
      clienteNome?.toLowerCase().includes(search.toLowerCase()) ||
      livroTitulo?.toLowerCase().includes(search.toLowerCase()) ||
      status.toLowerCase().includes(search.toLowerCase())
    );
  });

  return (
    <Box sx={{ padding: 3 }}>
      <Typography variant="h4" gutterBottom>
        Gerenciamento de Empréstimos
      </Typography>
      <Box
        sx={{
          marginBottom: 2,
          display: "flex",
          justifyContent: "space-between",
        }}
      >
        <TextField
          label="Pesquisar"
          variant="outlined"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          sx={{ width: "60%" }}
        />
        <Button
          variant="contained"
          color="primary"
          onClick={() => handleOpen(null)}
        >
          Novo Empréstimo
        </Button>
      </Box>
      <TableContainer
        component={Paper}
        sx={{
          boxShadow: "0 0 15px rgba(0, 0, 0, 0.1)",
          backgroundColor: "#f9f9f9",
        }}
      >
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>ID</TableCell>
              <TableCell>Nome do Cliente</TableCell>
              <TableCell>Título do Livro</TableCell>
              <TableCell>Data de Empréstimo</TableCell>
              <TableCell>Data Prevista</TableCell>
              <TableCell>Status</TableCell>
              <TableCell>Ações</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {filteredLoans.map((loan) => (
              <TableRow key={loan.id}>
                <TableCell>{loan.id}</TableCell>
                <TableCell>{clients.find((client) => client.id === loan.clienteId)?.nome}</TableCell>
                <TableCell>{books.find((book) => book.id === loan.livroId)?.nome}</TableCell>
                <TableCell>{loan.dataEmprestimo}</TableCell>
                <TableCell>{loan.dataPrevista}</TableCell>
                <TableCell>{loan.status}</TableCell>
                <TableCell>
                  <Button
                    variant="outlined"
                    color="primary"
                    onClick={() => handleOpen(loan)}
                    sx={{ marginRight: 1 }}
                  >
                    Editar
                  </Button>
                  <Button
                    variant="contained"
                    color="error"
                    onClick={() => handleDelete(loan.id)}
                  >
                    Deletar
                  </Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      {/* Modal de Edição/Criação */}
      <Modal open={open} onClose={handleClose}>
        <Box
          sx={{
            backgroundColor: "white",
            padding: 4,
            borderRadius: 2,
            boxShadow: 24,
            maxWidth: 500,
            margin: "auto",
            marginTop: "10%",
          }}
        >
          <Typography variant="h6" gutterBottom>
            {editingLoan ? "Editar Empréstimo" : "Novo Empréstimo"}
          </Typography>
          <Select
            name="clienteId"
            value={formData.clienteId}
            onChange={handleInputChange}
            fullWidth
            margin="normal"
          >
            <MenuItem value="">Selecione um Cliente</MenuItem>
            {clients.map((client) => (
              <MenuItem key={client.id} value={client.id}>
                {client.nome}
              </MenuItem>
            ))}
          </Select>
          <Select
            name="livroId"
            value={formData.livroId}
            onChange={handleInputChange}
            fullWidth
            margin="normal"
          >
            <MenuItem value="">Selecione um Livro</MenuItem>
            {books.map((book) => (
              <MenuItem key={book.id} value={book.id}>
                {book.nome}
              </MenuItem>
            ))}
          </Select>
          <TextField
            label="Data de Empréstimo"
            name="dataEmprestimo"
            type="date"
            variant="outlined"
            fullWidth
            margin="normal"
            value={formData.dataEmprestimo}
            onChange={handleInputChange}
            InputLabelProps={{ shrink: true }}
          />
          <TextField
            label="Data Prevista"
            name="dataPrevista"
            type="date"
            variant="outlined"
            fullWidth
            margin="normal"
            value={formData.dataPrevista}
            onChange={handleInputChange}
            InputLabelProps={{ shrink: true }}
          />
          <Select
            name="status"
            value={formData.status}
            onChange={handleInputChange}
            fullWidth
            margin="normal"
          >
            <MenuItem value="ativo">Ativo</MenuItem>
            <MenuItem value="concluido">Concluído</MenuItem>
            <MenuItem value="atrasado">Atrasado</MenuItem>
          </Select>
          <Button
            variant="contained"
            color="primary"
            onClick={handleSave}
            sx={{ marginTop: 2 }}
          >
            Salvar
          </Button>
        </Box>
      </Modal>

      {/* Notificações */}
      <Snackbar
        open={notification.open}
        autoHideDuration={6000}
        onClose={() => setNotification({ open: false, message: "", severity: "" })}
      >
        <Alert
          onClose={() => setNotification({ open: false, message: "", severity: "" })}
          severity={notification.severity}
          sx={{ width: "100%" }}
        >
          {notification.message}
        </Alert>
      </Snackbar>
    </Box>
  );
};

export default LoanManagement;
