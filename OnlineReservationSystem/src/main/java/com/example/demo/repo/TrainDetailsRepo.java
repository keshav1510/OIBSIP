//package com.example.demo.repo;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import com.example.demo.entity.TrainDetails;
//
//@Repository
//public interface TrainDetailsRepo extends JpaRepository<TrainDetails, Integer>{
//	
//	@Query("SELECT t FROM Train_Details WHERE t.trainNumber=?1")
//	TrainDetails findByTrainNumber(Integer trainNumber);
//}
