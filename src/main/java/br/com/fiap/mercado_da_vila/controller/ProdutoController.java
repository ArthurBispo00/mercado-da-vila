package br.com.fiap.mercado_da_vila.controller;

import br.com.fiap.mercado_da_vila.model.Produto;
import br.com.fiap.mercado_da_vila.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*; // Import estático para HATEOAS

@RestController
@RequestMapping("/mercado") // Endpoint base
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    // READ (GET All)
    @GetMapping
    public ResponseEntity<List<Produto>> getAllProdutos() {
        List<Produto> produtos = produtoRepository.findAll();
        if (produtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // Adicionando links HATEOAS para cada produto
        for (Produto produto : produtos) {
            produto.add(linkTo(methodOn(ProdutoController.class).getProdutoById(produto.getId())).withSelfRel());
        }
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }

    // READ (GET by ID)
    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
        Optional<Produto> produtoOpt = produtoRepository.findById(id);
        if (produtoOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Produto produto = produtoOpt.get();
        // Adicionando links HATEOAS (nível 3 de maturidade)
        produto.add(linkTo(methodOn(ProdutoController.class).getAllProdutos()).withRel("todos_os_produtos"));
        produto.add(linkTo(methodOn(ProdutoController.class).deleteProduto(produto.getId())).withRel("deletar_produto"));
        return new ResponseEntity<>(produto, HttpStatus.OK);
    }

    // CREATE (POST)
    @PostMapping
    public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) {
        Produto novoProduto = produtoRepository.save(produto);
        return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
    }

    // UPDATE (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody Produto produtoDetails) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setNome(produtoDetails.getNome());
                    produto.setTipo(produtoDetails.getTipo());
                    produto.setSetor(produtoDetails.getSetor());
                    produto.setTamanho(produtoDetails.getTamanho());
                    produto.setPreco(produtoDetails.getPreco());
                    Produto updatedProduto = produtoRepository.save(produto);
                    return new ResponseEntity<>(updatedProduto, HttpStatus.OK);
                }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        if (!produtoRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        produtoRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}