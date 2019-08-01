
import java.util.LinkedList;
import java.util.Scanner;

class lift{
	int dir, curFloor, status;
	LinkedList <Integer> stops = new LinkedList<Integer>();
	
	void singleLift_optimize_Algo(int floor, int LiftNo) {
		
		int di = this.dir;  int cF = this.curFloor;	
		
		if(di == 0 && floor > cF) {
			this.stops.add(floor);  this.dir = 1;
		}
	
		if(di == 0 && floor < cF) {
			this.stops.add(floor);  this.dir = -1;
		}
	
	    
	//up and floor > curFloor
		if(di == 1 && floor > cF) {
			int flag = 0;
			for(int i=0; i < this.stops.size(); i++) {
			
				if( floor == this.stops.get(i) ) {
					flag = 1;  break;
				}
			
				if( floor < this.stops.get(i) ) {
					this.stops.add(i,floor);  flag = 1;  break;
				}
			}
		
			if(flag == 0 && floor != cF) {
				this.stops.add(floor);
			}
		}
	
	
	//up and floor < curFloor
		if(di == 1 && floor < cF) {
			int flag = 0, last = this.stops.size()-1;
		
			if( floor < this.stops.get(last) ) {
				this.stops.add(floor);  flag = 1;
			}
		
			if(flag == 0 && floor != cF) {
				for(int i=last; i>=0; i--) {
					if(floor == this.stops.get(i)) 
						break;
					
					if( floor < this.stops.get(i) ) {
						this.stops.add(i+1,floor);  break;
					}
				}	
			}	
		}
	
	//down and floor < curFloor
		if(di == -1 && floor < cF) {
			int flag = 0;
			for(int i=0; i < this.stops.size(); i++) {
				if( floor == this.stops.get(i) ) {
					flag = 1;  break;
				}
				if( floor > this.stops.get(i) ) {
					this.stops.add(i,floor);  flag = 1;  break;
				}
			}
		
			if(flag == 0 && floor != cF) {
				this.stops.add(floor);
			}
		}
	
	
	//down and floor > curFloor
		if(di == -1 && floor > cF) {
			int flag = 0, last = this.stops.size()-1;
			if( floor > this.stops.get(last) ) {
				this.stops.add(floor);  flag = 1;
			}
		
			if(flag == 0 && floor != cF) {
				for(int i=last; i>=0; i--) {
					if(floor == this.stops.get(i)) 
						break;
					if( floor > this.stops.get(i) ) {
						this.stops.add(i+1,floor);  break;
					}
				}	
			}	
		}
	}

};


public class Lift{	
	public static void main(String[] args) throws InterruptedException {
		lift l[] = new lift[5];
		for(int i=1; i<=4; i++) {
			l[i] = new lift(); 
		}
		
		l[1].status = 0;  l[1].dir = -1;  l[1].curFloor = 8;  l[1].stops.add(7);
		l[2].status = 0;  l[2].dir =  0;  l[2].curFloor = 9;  
		l[3].status = 0;  l[3].dir =  1;  l[3].curFloor = 0;  l[3].stops.add(4); l[3].stops.add(6); 
		l[3].status = 0;  l[4].dir =  1;  l[4].curFloor = 5;  l[4].stops.add(6); l[4].stops.add(2);
		
		int flg =0, cost_index = 0;		int[] cost = new int[5];
		
		/*System.out.print("\nCurrent Status of Elevators\n");
		for(int j=1; j<=4; j++) 
			System.out.print("\nElevator "+j+" is at \t Floor "+l[j].curFloor  + " \t directn = "+l[j].dir +" \t\tStops = " + l[j].stops + "\n");	
		*/
	do {	
	    	
	    System.out.print("\nEnter Lift Call ( destFloor_li_liftNo / currFloor$Direction ) :  "); 	
		Scanner in = new Scanner(System.in);
		String s = in.nextLine();
		
		char c = 'n';  int stopno=0;
		
		if(s.length() >= 6)	{
			c = s.charAt(5);
			stopno = (int) s.charAt(4) - 48;
			if(c == 'i') 				//when lift is full
				l[stopno].status = 1; 
			if(c == 'f') 				//when lift is free
				l[stopno].status = 0; 
		}
		
		if(!s.isEmpty()) {
		char ch = s.charAt(1);	
		int floor = (int) s.charAt(0) - 48;
		flg = 0; int directn = 0;
			
			if(ch == 'l') {	
		    	int liftNo = (int) s.charAt(3) - 48;  
        	       l[liftNo].singleLift_optimize_Algo(floor, liftNo);
		    }
			if(ch == 'd') {
				directn = -1; flg = 1;
			}
			if(ch == 'u') {
				directn = 1;  flg = 1;
			}
			
			
			//call outside the lift
			if(flg == 1) {	
				
				for(int i=1; i<=4; i++) {
					
					if(l[i].dir == 0) {
						cost[i] = 5*Math.abs(floor - l[i].curFloor);	continue;
					}
					int max=0, min=99;
					if(l[i].stops.size() != 0) {
						max = l[i].stops.get(0); min = l[i].stops.get(0);	// check if max=min
						for(int m=1; m<l[i].stops.size(); m++) {
							if(l[i].stops.get(m) > max) {		// find max floor
								max = l[i].stops.get(m);
							}
							if(l[i].stops.get(m) < min) {		//find min floor
								min = l[i].stops.get(m);
							}
						}
					}
					
				// UseCases when lift dir = up
					if(l[i].dir == 1) {		
					 //Set1. floor > curFloor
						if(floor > l[i].curFloor) {	 
							if(directn == 1) {			// UseCase 1
								cost[i] = 5*Math.abs( floor - l[i].curFloor );
								for(int j=0; j<l[i].stops.size(); j++) {
									if( (l[i].stops.get(j) < floor) && (l[i].stops.get(j) > l[i].curFloor) ) {
										cost[i] += 10; 
									}
								}
							}
							if(directn == -1) {			// UseCase 2.1
								
								if(max >= floor) {		
									cost[i] = 5*( Math.abs(max - l[i].curFloor) + Math.abs(max - floor) );
									for(int j=0; j<l[i].stops.size(); j++) {
										if( l[i].stops.get(j) > l[i].curFloor ) {
											cost[i] += 10; 
										}
									}
								}
								if(max < floor) {		// UseCase 2.2
									cost[i] = 5*( Math.abs(max - l[i].curFloor) + Math.abs(floor - max) );
									for(int j=0; j<l[i].stops.size(); j++) {
										if( l[i].stops.get(j) > l[i].curFloor ) {
											cost[i] += 10; 
										}
									}
								}
							}	
						}
						
					//Set 2. floor < curFloor
						if(floor < l[i].curFloor) {
							if(directn == 1) {			// UseCase 3
								cost[i] = 5*( Math.abs(max - l[i].curFloor) + Math.abs(max - min) + Math.abs(floor - min) );
								for(int j=0; j<l[i].stops.size(); j++) {
									cost[i] += 10;
								}
							}
							if(directn == -1) {			// UseCase 4
								cost[i] = 5*( Math.abs(max - l[i].curFloor) + Math.abs(max - floor) );
								for(int j=0; j<l[i].stops.size(); j++) {
									if( l[i].stops.get(j) > floor ) {
										cost[i] += 10; 
									}
								}
							}
						}
					//Set 3. floor = curFloor
						if(floor == l[i].curFloor) {		// UseCase 5
							if(directn == 1) {
								cost[i] = 0;
							}
							if(directn == -1) {				// UseCase 6	
								cost[i] = 5*( Math.abs(max - l[i].curFloor) + Math.abs(max - floor) );
								for(int j=0; j<l[i].stops.size(); j++) {
									if( l[i].stops.get(j) > floor ) {
										cost[i] += 10; 
									}
								}
							}
						}	
					}
					
				// UseCases when lift dir = down
					if(l[i].dir == -1) {
					//Set 4. floor > curFloor
						if(floor > l[i].curFloor) {	 
							if(directn == 1) {			// UseCase 7 
								cost[i] = 5*( Math.abs(l[i].curFloor - min) + Math.abs(floor - min) );
								for(int j=0; j<l[i].stops.size(); j++) {
									if( l[i].stops.get(j) < floor ) {
										cost[i] += 10; 
									}
								}
							}
							if(directn == -1) {			// UseCase 8
								cost[i] = 5*( Math.abs(l[i].curFloor - min) + Math.abs(max - min) + Math.abs(max - floor) );
								for(int j=0; j<l[i].stops.size(); j++) {
										cost[i] += 10; 
								}
							}
						}	
					//Set 5. floor < curFloor
							if(floor < l[i].curFloor) {	 
								if(directn == 1) {			// UseCase 9 
									cost[i] = 5*( Math.abs(l[i].curFloor - min) + Math.abs(floor - min) );
									for(int j=0; j<l[i].stops.size(); j++) {
										if( l[i].stops.get(j) < l[i].curFloor ) {
											cost[i] += 10; 
										}
									}
								}
								if(directn == -1) {			// UseCase 10.1
									if(min <= floor) {
										cost[i] = 5*Math.abs( l[i].curFloor - floor );
										for(int j=0; j<l[i].stops.size(); j++) {
											if( (l[i].stops.get(j) < l[i].curFloor) && (l[i].stops.get(j) > floor) ) {
												cost[i] += 10; 
											}
										}
									}
									if(min > floor) {		// UseCase 10.2
										cost[i] = 5*( Math.abs(l[i].curFloor - min) + Math.abs(min - floor) );
										for(int j=0; j<l[i].stops.size(); j++) {
											if( (l[i].stops.get(j) < l[i].curFloor) && (l[i].stops.get(j) > floor) ) {
												cost[i] += 10; 
											}
										}
									}										
								}
							}
					//Set 6. floor = curFloor
						if(floor == l[i].curFloor) {		// UseCase 11
							if(directn == -1) {
								cost[i] = 0;
							}
							if(directn == 1) {				// UseCase 12
								cost[i] = 5*( Math.abs(l[i].curFloor - min) + Math.abs(floor - min) );
								for(int j=0; j<l[i].stops.size(); j++) {
									if( l[i].stops.get(j) < floor ) {
										cost[i] += 10; 
									}
								}
							}
						}
					}
				}
				
				int min = 99;
				for(int p=1; p<=4; p++) {
					if((cost[p] < min) && (l[p].status == 0)) {
						min = cost[p];		cost_index = p;
					}
				}
				l[cost_index].singleLift_optimize_Algo(floor, cost_index);	
				
				//display
			}
		
		} 
    		//display
	if(s.isEmpty() || !s.isEmpty()) {	
		if(c == 's') {				 //when stop button is pressed from inside the lift
			l[stopno].stops.clear();	l[stopno].dir = 0;
		}
		if(flg == 0) {
			for(int j=1; j<=4; j++) 
				System.out.print("\nElevator "+j+"  moves at \tFloor "+l[j].curFloor  + "       directn = "+l[j].dir +" \tStops = " + l[j].stops + "\n");	
		}
	 	
		if(flg == 1) {
			for(int j=1; j<=4; j++) 
				System.out.print("\nElevator "+j+"  moves at \tFloor = "+l[j].curFloor + "       directn = "+l[j].dir +" \tWait Time = "+cost[j]+" \t\tStops = " + l[j].stops + "\n");
			System.out.print("\n\t\t\tSo Elevator "+cost_index+" picks the request!");
		}
	    	System.out.print("\n");		flg = 0;
    	
    	//update dir, curfloor, pop stops for each lifts
		for(int j=1; j<=4; j++) {
			//lifts moving up
			if(l[j].dir == 1) {
				l[j].curFloor++;
					if(l[j].curFloor == l[j].stops.getFirst()) {
						System.out.print("\n\t\t\tSo Elevator "+j+"  will drop at  Floor "+l[j].stops.getFirst()+"!\n");
						l[j].stops.removeFirst();
						
						if(l[j].stops.size() == 0) {
							l[j].dir = 0;  continue;
						}
							if(l[j].stops.getFirst() < l[j].curFloor) {
								l[j].dir = -1;	continue;
							}
							
					}
			}
			if(l[j].dir == -1) {
				l[j].curFloor--;
					if(l[j].curFloor == l[j].stops.getFirst()) {
						System.out.print("\n\t\t\tSo Elevator "+j+"  will drop at  Floor "+l[j].stops.getFirst()+"!\n");
						l[j].stops.removeFirst();
						
						
						if(l[j].stops.size() == 0) {
							l[j].dir = 0;  continue;
						}
							if(l[j].stops.getFirst() > l[j].curFloor) {
								l[j].dir = 1;
							}
							
					}
			}
		}
	  }
		 
	 
	 } while (true);
	 
  }
}



