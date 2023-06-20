package ma.enset.patientsmvc.web;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.enset.patientsmvc.dao.Patient;
import ma.enset.patientsmvc.repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;
    @GetMapping(path="/user/index")
    public String patients(Model model,
                           @RequestParam(name="keyword",defaultValue = "") String keyword,
                           @RequestParam(name="page",defaultValue = "0") int page,
                           @RequestParam(name="size",defaultValue = "5") int size
                           ){

        Page<Patient> pagePatients = Page.empty();
        pagePatients = patientRepository.findByNomContains(keyword,PageRequest.of(page,size));

        model.addAttribute("listePatients",pagePatients.getContent());
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
    return "patients";
    }
    @GetMapping("/admin/delete")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@RequestParam Long id,@RequestParam String keyword,@RequestParam int page){
        patientRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
    }
    @GetMapping("/patients")
    @ResponseBody
    public List<Patient> listPatients(){
        return patientRepository.findAll();
    }

    @GetMapping("/user/formPatients")
    public String formPatients(Model model){
        model.addAttribute("patient",new Patient());
        return "formPatients";
    }
    @PostMapping(path="/user/savePatient")
    public String savePatient(Model model,@Valid Patient patient,BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "formPatients";
    patientRepository.save(patient);
    return "redirect:/formPatients";
    }

    @GetMapping(path="/admin/editPatient")
    public String editPatient(Model model,@RequestParam("id") Long id,@RequestParam String keyword,@RequestParam(defaultValue = "0") int page){
        model.addAttribute("patient",patientRepository.findById(id).get());
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "editPatient";
    }

    @PostMapping(path="/admin/saveEditPatient")
    public String saveEditPatient(Model model, @Valid Patient patient, BindingResult bindingResult , @RequestParam String keyword, @RequestParam(defaultValue = "0") int page){
        if(bindingResult.hasErrors()) return "editPatient";
        patientRepository.save(patient);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }
}
