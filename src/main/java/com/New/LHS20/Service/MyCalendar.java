//package com.New.LHS20.Service;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//
//import org.springframework.stereotype.Service;
//
//import com.New.LHS20.Entity.Appointment;
///**
// * Write a description of class MyCalendar here.
// * 
// * @author (purna.chunduri)
// * @version (28 Aug 2015)
// */
//@Service
//public class MyCalendar {
//  // instance variables - replace the example below with your own
//  private String name;
//  //SimpleDateFormat form;// =new SimpleDateFormat("dd/MM/yy HH:mm:ss");
//  ArrayList<Appointment> appoint;
//  int slt;
//  /**
//   * Constructor for objects of class MyCalendar.
//   */
//  public MyCalendar(String name) {
//    // initialise instance variables
//    this.name = name;
//    slt = 0;
//  }
//  
//  /**
//   * @param slot which slot is added to list.
//   * @return true if the slot is added.
//   */
//  public boolean createAppointmentSlot(Appointment slot) {
//    int flag = 0;
//    if (slt == 0) {
//      appoint = new ArrayList<Appointment>();
//      appoint.add(slot);
//      slt++;
//      return true;
//    } else {
//      for (int cnt = 0; cnt < appoint.size(); cnt++) {
//        if ((appoint.get(cnt).getEnd()).compareTo(slot.getDat()) < 0) {
//          flag = 1;
//        }
//      }
//      if (flag == 1) {
//        appoint.add(slot);
//        return true;
//      }
//    }
//    return false;
//  }
//  
//  /**
//   * @param name name of the person who wants to ask appointment.
//   * @param place the place where to meet.
//   * @param date appointment date.
//   * @param dur how much time if they needs.
//   * @return true if a slot is booked to particular person
//   */
//  public boolean bookAppointment(String name, String date, int dur, String place) {
//    int flag = 0;
//    Date date1 = new Date();
//    SimpleDateFormat form = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
//    try {
//      date1 = form.parse(date);
//    } catch (Exception e) {
//      System.out.println();  
//    }
//    int temp = 0;
//    for (int cnt = 0; cnt < appoint.size(); cnt++) {
//      if ( date1.equals(appoint.get(cnt).getDat())) {
//        //System.out.print("true");
//        temp = cnt;
//        flag = 1;
//      }
//    }
//    if (flag == 1) {
//      if (appoint.get(temp).getName().equals("")) {
//        appoint.get(temp).setName(name);
//        appoint.get(temp).setPlace(place);
//        return true;
//      } else {
//        return false;
//      }
//    }
//    return false;
//  }
//  
//  /**
//   * @param date takes the date to cancel appointment.
//   * @return true if the appointment of particular date is cancel.
//   */
//  public boolean cancelAppointment(String date) {
//    Date date1 = new Date();
//    SimpleDateFormat form = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
//    try {
//      date1 = form.parse(date);
//    } catch (Exception e) {
//      System.out.println();  
//    }
//    for (int cnt = 0; cnt < appoint.size(); cnt++) {
//      if (date1.equals(appoint.get(cnt).getDat())) {
//        appoint.get(cnt).setName("");
//        appoint.get(cnt).setPlace("");
//        return true;
//      }
//    }
//    return false;
//  }
//}
