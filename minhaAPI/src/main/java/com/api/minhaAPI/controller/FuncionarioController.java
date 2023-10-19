package com.api.minhaAPI.controller;

import com.api.minhaAPI.dtos.FuncionarioRecordDto;
import com.api.minhaAPI.model.FuncionarioModel;
import com.api.minhaAPI.repositories.FuncionarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class FuncionarioController {

    @Autowired
    FuncionarioRepository funcionarioRepository;

    @PostMapping("/cadastrar")
    public ResponseEntity<FuncionarioModel> cadastrarFuncionaro(@RequestBody FuncionarioRecordDto funcionarioRecordDto){
        var funcionarioModel = new FuncionarioModel();
        BeanUtils.copyProperties(funcionarioRecordDto, funcionarioModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioRepository.save(funcionarioModel));
    }

    @GetMapping("/funcionarios")
    public ResponseEntity<List<FuncionarioModel>> getAllFuncionarios(){
        return ResponseEntity.status(HttpStatus.OK).body(funcionarioRepository.findAll());
    }

    @GetMapping("/funcionarios/{id}")
    public ResponseEntity<Object> getOneFuncionario(@PathVariable(value = "id")UUID id){
        Optional<FuncionarioModel> funcionarioID = funcionarioRepository.findById(id);
        if (funcionarioID.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionario nao encontrado!!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(funcionarioID.get());

    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Object> atualizarFuncionario(@PathVariable(value = "id")UUID id, @RequestBody FuncionarioRecordDto funcionarioRecordDto ) {
        Optional<FuncionarioModel> funcionarioID = funcionarioRepository.findById(id);
        if (funcionarioID.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionario nao encontrado!!");
        }
        var funcionarioModel = funcionarioID.get();
        BeanUtils.copyProperties(funcionarioRecordDto, funcionarioModel);
        return ResponseEntity.status(HttpStatus.OK).body(funcionarioRepository.save(funcionarioModel));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Object> deletarFuncionario(@PathVariable(value = "id")UUID id) {
        Optional<FuncionarioModel> funcionarioID = funcionarioRepository.findById(id);
        if (funcionarioID.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionario nao encontrado!!");
        }
        funcionarioRepository.delete(funcionarioID.get());
        return ResponseEntity.status(HttpStatus.OK).body("O Funcionario foi deletado com sucesso!!");
    }

}
