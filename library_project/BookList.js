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
  Box,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  TextField,
  MenuItem,
} from "@mui/material";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function BookList() {
  const [books, setBooks] = useState([]);
  const [filteredBooks, setFilteredBooks] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedBook, setSelectedBook] = useState(null);
  const [editDialogOpen, setEditDialogOpen] = useState(false);
  const [deleteConfirmDialogOpen, setDeleteConfirmDialogOpen] = useState(false);
  const [bookToDelete, setBookToDelete] = useState(null);

  const categories = [
"Romance",
"Fantasia",
"Aventura",
"Terror",
"Suspense",
"Mistério",
"Drama",
"Biografia",
"Autobiografia",
"História",
"História em Quadrinhos",
"Poesia",
"Ensaios",
"Literatura Clássica",
"Literatura Contemporânea",
"Policial",
"Jovem Adulto",
"Infantil",
"Didático",
"Autoajuda",
"Psicologia",
"Filosofia",
"Religioso",
"Espiritualidade",
"Humor",
"Negócios",
"Tecnologia",
"Ciência",
"Culinária",
"Viagem",
"Distopia",
"Realismo Mágico",
"Épico",
"Erótico"

  ];

  const fetchBooks = async () => {
    try {
      const username = localStorage.getItem("username");
      const password = localStorage.getItem("password");
      const basicAuth = btoa(`${username}:${password}`);

      const response = await axios.get("http://localhost:8080/api/livros", {
        headers: {
          Authorization: `Basic ${basicAuth}`,
        },
      });
      setBooks(response.data);
      setFilteredBooks(response.data);
    } catch (error) {
      console.error("Erro ao buscar livros:", error);
      toast.error("Erro ao carregar a lista de livros.");
    }
  };

  const handleSearch = (e) => {
    const term = e.target.value.toLowerCase();
    setSearchTerm(term);
    const filtered = books.filter(
      (book) =>
        book.nome.toLowerCase().includes(term) ||
        book.codigoBarras.includes(term)
    );
    setFilteredBooks(filtered);
  };

  const handleDelete = async () => {
    try {
      const userType = localStorage.getItem("userType");
      if (userType !== "administrador") {
        toast.error("Ação não permitida. Apenas administradores podem deletar livros.");
        return;
      }

      const username = localStorage.getItem("username");
      const password = localStorage.getItem("password");
      const basicAuth = btoa(`${username}:${password}`);

      const cleanedCodigoBarras = bookToDelete.codigoBarras.replace(/\D/g, "");

      await axios.delete(
        `http://localhost:8080/api/livros/${cleanedCodigoBarras}`,
        {
          headers: {
            Authorization: `Basic ${basicAuth}`,
          },
        }
      );
      toast.success("Livro deletado com sucesso!");
      fetchBooks();
      setDeleteConfirmDialogOpen(false);
    } catch (error) {
      console.error("Erro ao deletar livro:", error.response || error);
      toast.error("Erro ao deletar livro.");
    }
  };

  const handleEditSave = async () => {
    try {
      const username = localStorage.getItem("username");
      const password = localStorage.getItem("password");
      const basicAuth = btoa(`${username}:${password}`);

      const cleanedBook = {
        ...selectedBook,
        codigoBarras: selectedBook.codigoBarras.replace(/\D/g, ""),
      };

      await axios.put(
        `http://localhost:8080/api/livros/${cleanedBook.codigoBarras}`,
        cleanedBook,
        {
          headers: {
            Authorization: `Basic ${basicAuth}`,
            "Content-Type": "application/json",
          },
        }
      );
      toast.success("Livro atualizado com sucesso!");
      fetchBooks();
      handleEditClose();
    } catch (error) {
      console.error("Erro ao atualizar livro:", error.response || error);
      toast.error("Erro ao atualizar livro.");
    }
  };

  const handleEditOpen = (book) => {
    setSelectedBook(book);
    setEditDialogOpen(true);
  };

  const handleEditClose = () => {
    setSelectedBook(null);
    setEditDialogOpen(false);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setSelectedBook((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleDeleteConfirmOpen = (book) => {
    setBookToDelete(book);
    setDeleteConfirmDialogOpen(true);
  };

  const handleDeleteConfirmClose = () => {
    setBookToDelete(null);
    setDeleteConfirmDialogOpen(false);
  };

  useEffect(() => {
    fetchBooks();
  }, []);

  return (
    <Box sx={{ padding: 3 }}>
      <Typography variant="h4" gutterBottom align="center">
        Lista de Livros
      </Typography>
      <Box sx={{ display: "flex", justifyContent: "flex-end", marginBottom: 2 }}>
        <TextField
          label="Pesquisar"
          variant="outlined"
          value={searchTerm}
          onChange={handleSearch}
          placeholder="Digite o nome ou código de barras"
          fullWidth
          sx={{ maxWidth: 400 }}
        />
      </Box>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Código de Barras</TableCell>
              <TableCell>Nome</TableCell>
              <TableCell>Autor</TableCell>
              <TableCell>Edição</TableCell>
              <TableCell>Categoria</TableCell>
              <TableCell>Ações</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {filteredBooks.map((book) => (
              <TableRow key={book.codigoBarras}>
                <TableCell>{book.codigoBarras}</TableCell>
                <TableCell>{book.nome}</TableCell>
                <TableCell>{book.autor}</TableCell>
                <TableCell>{book.edicao}</TableCell>
                <TableCell>{book.categoria}</TableCell>
                <TableCell>
                  <Box sx={{ display: "flex", gap: 1 }}>
                    <Button
                      variant="contained"
                      color="primary"
                      size="small"
                      onClick={() => handleEditOpen(book)}
                    >
                      Editar
                    </Button>
                    <Button
                      variant="contained"
                      color="error"
                      size="small"
                      onClick={() => handleDeleteConfirmOpen(book)}
                      disabled={localStorage.getItem("userType") !== "administrador"}
                    >
                      Deletar
                    </Button>
                  </Box>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      {/* Dialog para Confirmar Exclusão */}
      <Dialog open={deleteConfirmDialogOpen} onClose={handleDeleteConfirmClose}>
        <DialogTitle>Confirmar Exclusão</DialogTitle>
        <DialogContent>
          Tem certeza que deseja excluir o livro{" "}
          <strong>{bookToDelete?.nome}</strong>?
        </DialogContent>
        <DialogActions>
          <Button onClick={handleDeleteConfirmClose} color="primary">
            Cancelar
          </Button>
          <Button onClick={handleDelete} color="error">
            Confirmar
          </Button>
        </DialogActions>
      </Dialog>

      {/* Dialog para Editar Livro */}
      <Dialog open={editDialogOpen} onClose={handleEditClose}>
        <DialogTitle>Editar Livro</DialogTitle>
        <DialogContent>
          <TextField
            label="Nome"
            name="nome"
            fullWidth
            margin="normal"
            value={selectedBook?.nome || ""}
            onChange={handleInputChange}
          />
          <TextField
            label="Autor"
            name="autor"
            fullWidth
            margin="normal"
            value={selectedBook?.autor || ""}
            onChange={handleInputChange}
          />
          <TextField
            label="Edição"
            name="edicao"
            fullWidth
            margin="normal"
            value={selectedBook?.edicao || ""}
            onChange={handleInputChange}
          />
          <TextField
            select
            label="Categoria"
            name="categoria"
            fullWidth
            margin="normal"
            value={selectedBook?.categoria || ""}
            onChange={handleInputChange}
          >
            {categories.map((category) => (
              <MenuItem key={category} value={category}>
                {category}
              </MenuItem>
            ))}
          </TextField>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleEditClose} color="primary">
            Cancelar
          </Button>
          <Button onClick={handleEditSave} color="primary">
            Salvar
          </Button>
        </DialogActions>
      </Dialog>

      <ToastContainer />
    </Box>
  );
}

export default BookList;
