package com.vitorraphael.gestor_comercial.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vitorraphael.gestor_comercial.exception.RecursoNaoEncontradoException;
import com.vitorraphael.gestor_comercial.model.FichaTecnica;
import com.vitorraphael.gestor_comercial.model.MateriaPrima;
import com.vitorraphael.gestor_comercial.model.Produto;
import com.vitorraphael.gestor_comercial.repository.FichaTecnicaRepository;

@Service
public class FichaTecnicaService {

    private final FichaTecnicaRepository fichaTecnicaRepository;
    private final ProdutoService produtoService;
    private final MateriaPrimaService materiaPrimaService;

    public FichaTecnicaService(FichaTecnicaRepository fichaTecnicaRepository, ProdutoService produtoService,
            MateriaPrimaService materiaPrimaService) {
        this.fichaTecnicaRepository = fichaTecnicaRepository;
        this.produtoService = produtoService;
        this.materiaPrimaService = materiaPrimaService;
    }

    public FichaTecnica associar(Long produtoId, Long materiaPrimaId, BigDecimal quantidadeUsada) {
        Produto produto = produtoService.buscarPorId(produtoId);
        MateriaPrima materiaPrima = materiaPrimaService.buscarPorId(materiaPrimaId);

        FichaTecnica fichaTecnica = new FichaTecnica();
        fichaTecnica.setProduto(produto);
        fichaTecnica.setMateriaPrima(materiaPrima);
        fichaTecnica.setQuantidadeUsada(quantidadeUsada);
        return fichaTecnicaRepository.save(fichaTecnica);
    }

    public void remover(Long fichaTecnicaId) {
        buscarPorId(fichaTecnicaId);
        fichaTecnicaRepository.deleteById(fichaTecnicaId);
    }

    public List<FichaTecnica> listarPorProduto(Long produtoId) {
        return fichaTecnicaRepository.findByProdutoId(produtoId);
    }

    public FichaTecnica buscarPorId(Long id) {
        return fichaTecnicaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Ficha técnica não encontrada: " + id));
    }
}
