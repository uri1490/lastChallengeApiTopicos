package com.topicos.APITopicos.controller;

import com.topicos.APITopicos.model.TopicoModel;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.topicos.APITopicos.service.TopicoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
public class TopicoController {
    @Autowired
    TopicoService topicoService;
    @GetMapping("/index")
    ModelAndView index(){
        List<TopicoModel> topicoModels= topicoService.buscarTopico();
        System.out.println("testing");
        //

        //
        for(TopicoModel x : topicoModels){
            System.out.println("contacto"+x.getAutor());}
        return new ModelAndView("index").addObject("topicos",topicoModels);
    }
    private List<TopicoModel> users = new ArrayList<>();

    /*@GetMapping(value = "topico")
    public ResponseEntity<List<TopicoModel>> findAll(){
        return ResponseEntity.ok(users);
    }*/
    @GetMapping("/getTopicosSwagger")
    ResponseEntity getTopicosSwagger(){
        List<TopicoModel> topicoModels= topicoService.buscarTopico();
        System.out.println("testing");
        for(TopicoModel x : topicoModels){
            System.out.println("contacto"+x.getAutor());}
        return ResponseEntity.ok(topicoModels);
    }
    @GetMapping("/nuevoTopico")
    ModelAndView nuevo(){
        TopicoModel x = new TopicoModel();
        LocalDate today = LocalDate.now();
        x.setFechaCreacion(today);
        return new ModelAndView("form").addObject("topico",x);
    }
    @PostMapping("/nuevoTopico")
    ModelAndView crear(@Validated TopicoModel topico, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        System.out.println(topico);
        //LocalDate today = LocalDate.now();

        //DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //String formattedDate = today.format(dateTimeFormatter);
        //topico.setFechaCreacion(today);
        if(bindingResult.hasErrors()){
            System.out.println("error");

            return new ModelAndView("form").addObject("topico",topico);
        }

        topicoService.createTopico(topico);///aqui cambie el c service
        redirectAttributes.addFlashAttribute("msgExito","registro exitoso");
        redirectAttributes.addFlashAttribute("page",1);
        return new ModelAndView("redirect:/");
    }
    @GetMapping("/{id}/editar")
    ModelAndView editar(@PathVariable Integer id){
        TopicoModel topicoModel=topicoService.buscarTopico(id).orElseThrow(EntityExistsException::new);
        return new ModelAndView("form").addObject("topico",topicoModel);
    }
    @PostMapping("/{id}/editar")
    ModelAndView actualizar(
            @PathVariable Integer id,
            @Validated TopicoModel topicoModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes){
        topicoService.buscarTopico(id);
        if(bindingResult.hasErrors()){
            System.out.println("error");
            return new ModelAndView("form").addObject("topico",topicoModel);
        }
        topicoModel.setId(id);
        topicoService.createTopico(topicoModel);
        redirectAttributes.addFlashAttribute("msgExito","Actualizacion exitoso");
        redirectAttributes.addFlashAttribute("page",1);
        return new ModelAndView("redirect:/");
    }
    @PutMapping("/{id}/editarSwagger")
    ResponseEntity actualizarSwagger(
            @PathVariable Integer id,
            @Valid @RequestBody TopicoModel topicoModel,
            BindingResult bindingResult
            ){
        topicoService.buscarTopico(id).orElseThrow(EntityNotFoundException::new);
        if(bindingResult.hasErrors()){
            System.out.println("error:"+ topicoModel);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datos incorrectos\n");
        }
        topicoModel.setId(id);
        topicoService.createTopico(topicoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("Se actualizo el usuario correctamente");
    }
    @PostMapping("/nuevoTopicoSwagger")
    ResponseEntity crearSwagger(@Valid @RequestBody TopicoModel topico){
        System.out.println(topico.getAutor());
        if(topico.getAutor() ==null){
            System.out.println("error");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datos incorretos\n");
        }
        else{
            topicoService.createTopico(topico);///aqui cambie el c service
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Datos agregados correctamente\n");
    }
    @GetMapping("/{id}")
    ModelAndView borrar(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes){
        System.out.println("borrando"+ id);
        topicoService.buscarTopico(id).orElseThrow(EntityNotFoundException::new);
        topicoService.borrarTopico(id);
        redirectAttributes.addFlashAttribute("msgExito","borrado exitoso");
        redirectAttributes.addFlashAttribute("page",1);
        return new ModelAndView("redirect:/");
    }
    @GetMapping("/{id}/obtenerTopicoSwagger")
    ResponseEntity obtenerTopicoSwagger(@PathVariable Integer id){
        TopicoModel topicoModel=topicoService.buscarTopico(id).orElseThrow(EntityExistsException::new);
        if(topicoModel==null){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el elemento");
        }
        return ResponseEntity.ok(topicoModel);
    }
    @DeleteMapping("/deleteSwagger/{id}")
    public ResponseEntity<String> delet(@PathVariable int id){
        System.out.println("borrando test"+ id);
        topicoService.borrarTopico(id);
        return ResponseEntity.ok("Se ha borrado ek usuario");
    }
    @GetMapping(value="/")
    ModelAndView findAll(@RequestParam Map<String, Object> params, Model model){
        //
       int page=0;
System.out.println("paramtero page"+params.get("page"));
        System.out.println("paramtero page"+params);
        System.out.println("paramtero page" + model);
        page = params.get("page") !=null ? (Integer.valueOf(params.get("page").toString()))-1: 0;
        PageRequest pageRequest = PageRequest.of(page, 3);
        Page<TopicoModel>pageTopico=topicoService.getAll(pageRequest);
        System.out.println(pageTopico.getContent());
        int totalPage=pageTopico.getTotalPages();
        if (totalPage>0){
            List<Integer>pages= IntStream.rangeClosed(1,totalPage).boxed().collect(Collectors.toList());
            model.addAttribute("pages", pages);

        }
        LocalDate today = LocalDate.now();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = today.format(dateTimeFormatter);
        model.addAttribute("list",pageTopico.getContent());
        model.addAttribute("current",page+1);
        model.addAttribute("next",page+2);
        model.addAttribute("prev",page);
        model.addAttribute("last",totalPage);
        model.addAttribute("fecha",formattedDate);

        Map<String,TopicoModel>mapaAttr = (Map<String, TopicoModel>) model;
        return  new ModelAndView("/index").addAllObjects(mapaAttr);
    }
    //@RequestMapping(value="/logout", method = RequestMethod.GET)
@GetMapping("/login")
    ModelAndView  loginPage(){


        return  new ModelAndView("/login");
}
}
