package com.example.demo.controller;

import java.util.Random;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.LoginCredential;
import com.example.demo.entity.PNRCancel;
import com.example.demo.entity.ReservationDetails;
import com.example.demo.repo.ReservationRepo;
//import com.example.demo.service.TrainDetailsService;

@Controller
public class ReservationController {
	@Autowired
	ReservationRepo repo;
	
	public Long pnrNumber;

//	@Autowired
//	TrainDetailsService trainDetailsService;

	@GetMapping("/login")
	public String showLogin() {
		return "login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute(name = "loginForm") LoginCredential login, Model m) {
		String uname = login.getUsername();
		String pass = login.getPassword();
		if (uname != null && pass != null) {
			if (uname.equals("Admin") && pass.equals("Admin@123")) {
				m.addAttribute("uname", uname);
				m.addAttribute("pass", pass);
				return "redirect:/addnew";
			}
		}
		m.addAttribute("error", "Incorrect Username & Password");
		return "login";
	}

	@GetMapping("/addnew")
	public String addNewEmployee(Model model) {
		ReservationDetails emptyObj = new ReservationDetails();
		model.addAttribute("reservationObj", emptyObj);
		return "reservation";
	}

//	@PostMapping("/save")
//	public String saveData(@ModelAttribute("reservationDetails") ReservationDetails reservationDetails) {
//		System.out.println(reservationDetails);
//		reservationService.save(reservationDetails);
//		return "redirect:/addnew";
//	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST, params="action=savedata")
	public String save(@ModelAttribute("reservationDetails") ReservationDetails reservationDetails,Model model) {

		Random random = new Random();
		this.pnrNumber = (long)(random.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
        reservationDetails.setPnr(this.pnrNumber.toString());
        
		model.addAttribute("pnr",this.pnrNumber);
		
		repo.save(reservationDetails);
		return "popup";
	}
	
	@PostMapping(value="/popup")
	public String popup() {
		return "redirect:/addnew";
	}

	@RequestMapping(value="/save", method=RequestMethod.POST, params="action=cancelform")
	public String cancelForm(@ModelAttribute("reservationDetails") ReservationDetails reservationDetails, Model model) {
		return "redirect:/addNew";
	}

	@RequestMapping(value="/save", method=RequestMethod.POST, params="action=cancelpage")
	public String cancelTicket(@ModelAttribute("reservationDetails") ReservationDetails reservationDetails, Model model) {
		return "redirect:/cancel";
	}



	@RequestMapping(value="/delete", method=RequestMethod.GET, params="action=cancelpnr")
	public String cancelPage( Model m) {
		PNRCancel obj = new PNRCancel();
		m.addAttribute("pnr", obj);
		return "pnr";
	}

	@GetMapping("/cancel")
	public String cancelTicket(Model model) {
		PNRCancel pnrCancel = new PNRCancel();
		model.addAttribute("pnrCancel", pnrCancel);
		return "cancel";
	}

	@PostMapping("/delete")
	@Transactional
	public String deleteRecord(@ModelAttribute("pnrCancel") PNRCancel pnrCancel, Model m) {
		repo.deleteById(pnrCancel.getPnr().toString());
		return "redirect:/addnew";
	}
//	@GetMapping("/fetchTrain")
//	public String fetchTrainName(@PathVariable(value = "trainNumber") Integer trainNumber) {
//		return trainDetailsService.getByTrainNumber(trainNumber).getTrainName();
//	}

}
