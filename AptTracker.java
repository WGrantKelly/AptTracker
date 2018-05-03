package P3;

import java.util.*;

public class AptTracker{

	//initialize scanner object for all driver instructions
	private static Scanner keyboard = new Scanner(System.in);

	//initialize all data structures
	private static Apartment[] apts = new Apartment[29];
	private static int[] indirection = new int[29];	
	private static Hashtable<String, Integer> hash = new Hashtable<String, Integer>(29);

	public static void main(String[] args){
                
		boolean done = false;
		System.out.println("\t\tWelcome to Apartment Tracker!\n\n* Lets get started *\n");
                int count = 0;
                for(int a : indirection){
                    indirection[count] = -1;
                    count++;
                    
                }
		while(!done){
			printOptions();
			int i = Integer.parseInt(keyboard.nextLine());
			switch(i){
				case 1:
					add();
					break;
				case 2:
					update();
					break;
				case 3:
					remove();
					break;
				case 4:
					lowestPrice();
					break;
				case 5:
					highestSqFt();
					break;
				case 6:
					lowestPriceCity();
					break;
				case 7:
					highestSqFtCity();
					break;
				default:
					System.out.println("please enter a valid integer!\n");
					break;
			}
		}
		
	}
	public static void printOptions(){
		System.out.println("1. Add an apartment");
		System.out.println("2. Update an Apartment");
		System.out.println("3. Remove an apartment from consideration");
		System.out.println("4. Retrieve the lowest price apartment");
		System.out.println("5. Retrieve the highest square footage apartment");
		System.out.println("6. Retrieve the lowest price apartment by city");
		System.out.println("7. Retrieve the highest square footage apartment by city");
	}
	public static void add(){

		//generate an apartment object

		Apartment i = new Apartment();
		System.out.println("Enter a street name: ");
		i.setStreet(keyboard.nextLine());
		System.out.println("Enter an apartment Number: ");
		i.setAptNumber(Integer.parseInt(keyboard.nextLine()));
		System.out.println("Enter the city it's located in: ");
		i.setCity(keyboard.nextLine());
		System.out.println("Enter the zip code: ");
		i.setZip(Integer.parseInt(keyboard.nextLine()));
		System.out.println("Enter the list price: ");
		i.setPrice(Integer.parseInt(keyboard.nextLine()));
		System.out.println("Enter the square footage: ");
		i.setSqFootage(Integer.parseInt(keyboard.nextLine()));

		//check if price heap is full before trying to add, if it is not full, add.

		if(apts.length!=Heap.getSize(apts)){

			//add the apartment to the heap
			apts[Heap.getSize(apts)] = i;
			
			//Add the apartment to the hash map
                        
			hash.put(generateHashKey(i), (int)sfold(generateHashKey(i), apts.length));
			indirection[hash.get(generateHashKey(i))] = Heap.getSize(apts)-1;
			//sort the apartments to return to proper heap state using Heap sort
			apts = Heap.sort(apts, "footage");
                        //refresh the indirection array with the new values
                        refreshIndirection();

			System.out.println("\t\t* List of Apartments in apts After Sort *\n");
			printapts(apts);
		}
		else{
			System.out.println("Heap is full!");
		}
	}
	public static void update(){

		Apartment a = findApartment();
		System.out.print("Would you like to change the list price? (y/n): ");
		String response = keyboard.nextLine();

		//reset the price in the specific apartment
		if(response.equals("y")||response.equals("Y")||response.equals("yes")||response.equals("Yes")){
			System.out.print("Please enter the new list price: ");
			a.setPrice(Integer.parseInt(keyboard.nextLine()));
		}
		//sort the heap after the price change
		apts = Heap.sort(apts, "footage");
		//refresh the indirection array with the new values
                refreshIndirection();
		System.out.println("\t\t* List of Apartments in apts After Sort *\n");
		printapts(apts);
		


	}
	public static void remove(){
		//find the apartment to be removed
		Apartment a = findApartment();

		//remove the apartment from the heap
		apts[indirection[hash.get(generateHashKey(a))]] = null;

		//remove the apartment from the indirection table
		indirection[hash.get(generateHashKey(a))] = -1;

		//sort the heap after removal
		apts = Heap.sort(apts, "footage");
                refreshIndirection();
		System.out.println("\t\t* List of Apartments in apts After Sort *\n");
		printapts(apts);




	}
	public static void lowestPrice(){
		apts = Heap.sort(apts, "price");
		System.out.println("\t\t* List of Apartments in apts After Sort *\n");
		printapts(apts);
		System.out.printf("\t\t%s\n\n","*   This listing is the best match for your search  *");
		printApt(apts[0]);

	}
	public static void highestSqFt(){
		apts = Heap.sort(apts, "footage");
                refreshIndirection();
		System.out.println("\t\t* List of Apartments in apts After Sort *\n");
		printapts(apts);
		System.out.printf("\t\t%s\n\n","*   This listing is the best match for your search  *");
		printApt(apts[0]);
	}
	public static void lowestPriceCity(){
		System.out.println("Enter the city you would like to look at: ");
		String city = keyboard.nextLine();
		apts = Heap.sort(apts, "price");
                refreshIndirection();
		System.out.println("\t\t* List of Apartments in apts After Sort *\n");
		printapts(apts);
		int count = -1;
		for(Apartment a : apts){
			count++;
			if(a.getCity().equals(city))
				break;
		}
		System.out.printf("\t\t%s\n\n","*   This listing is the best match for your search  *");
		printApt(apts[count]);
	}
	public static void highestSqFtCity(){
		System.out.println("Enter the city you would like to look at: ");
		String city = keyboard.nextLine();
		apts = Heap.sort(apts, "footage");
                refreshIndirection();
		System.out.println("\t\t* List of Apartments in apts After Sort *\n");
		printapts(apts);
		int count = -1;
		for(Apartment a : apts){
			count++;
			if(a.getCity().equals(city))
				break;
		}
		printApt(apts[count]);
	}
	public static long sfold(String s, int M) {
    	int intLength = s.length() / 4;
    	long sum = 0;
    	for (int j = 0; j < intLength; j++) {
      		char c[] = s.substring(j * 4, (j * 4) + 4).toCharArray();
      		long mult = 1;
       		for (int k = 0; k < c.length; k++) {
	 			sum += c[k] * mult;
	 			mult *= 256;
       		}
     	}

     	char c[] = s.substring(intLength * 4).toCharArray();
     	long mult = 1;
     	for (int k = 0; k < c.length; k++) {
       		sum += c[k] * mult;
       		mult *= 256;
     	}

     return(Math.abs(sum) % M);
    }

    public static String generateHashKey(Apartment a){
        	//initialize key maker for the hashmap
        StringBuilder hashKey = new StringBuilder();
    	hashKey.append(a.getStreet());
    	hashKey.append(a.getAptNumber());
    	hashKey.append(a.getZip());
    	return hashKey.toString();
    }

    public static String generateHashKey(String street, int aptNumber, int zip){
        	//initialize key maker for the hashmap
        StringBuilder hashKey = new StringBuilder();
    	hashKey.append(street);
    	hashKey.append(aptNumber);
    	hashKey.append(zip);
    	return hashKey.toString();
    }


    public static void refreshIndirection(){
    	int count = 0;
        for(Apartment a : apts)
        {
            if(a!=null){
                indirection[hash.get(generateHashKey(a))] = getIndex(a);
                count++;
            }

        }
    }

    public static Apartment findApartment(){
    	System.out.println("Enter the street name: ");
		String street = keyboard.nextLine();
		System.out.println("Enter the apartment Number: ");
		int aptNumber = Integer.parseInt(keyboard.nextLine());
		System.out.println("Enter the zip code: ");
		int zip = Integer.parseInt(keyboard.nextLine());

		Apartment a = apts[indirection[hash.get(generateHashKey(street, aptNumber, zip))]];
		return a;
    }

    public static void printApt(Apartment a){
    	System.out.printf("Street Address:\t\t%s\n",a.getStreet());
    	System.out.printf("Apartment Number:\t%s\n",a.getAptNumber());
    	System.out.printf("City:\t\t\t%s\n",a.getCity());
    	System.out.printf("Zip Code:\t\t%s\n",a.getZip());
    	System.out.printf("Square Footage:\t\t%s\n",a.getSqFootage());
    	System.out.printf("List Price:\t\t%s\n",a.getPrice());
    }

    public static void printIndirectionArray() {
        for (int i = 0; i < indirection.length; i++) {
            System.out.println(indirection[i]);
        }
    }

    public static void printapts(Apartment[] a){
    	for (int i = 0; i < a.length; i++) {
    		if(a[i]!=null){
    			System.out.println("\nApartment "+(i+1)+": \n");
    			printApt(a[i]);
    			System.out.println("\n");
    		}
        }
    }
    
    public static int getIndex(Apartment a){
        int count = -1;
        for(Apartment b : apts){
            count++;
            if(isIdentical(a, b))
                return count;
        }
        return -1;
    }
    
    public static boolean isIdentical(Apartment a, Apartment b){
        return(a.getStreet().equals(b.getStreet())&&
               a.getAptNumber()==b.getAptNumber()&&
               a.getCity().equals(b.getCity())&&
               a.getZip()==b.getZip()&&
               a.getSqFootage()==b.getSqFootage()&&
               a.getPrice()==b.getPrice());
    }

}