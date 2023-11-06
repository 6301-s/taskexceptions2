package Sample;
import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
class bookCheckedout extends Exception{
	public bookCheckedout(String str) {
		super(str);
	}
}
class booknotcheckedout extends Exception{
	public booknotcheckedout(String str) {
		super(str);
	}
}
class Book {
    private int ISBN;
    
    private String title;
    private boolean isCheckedOut;

    public Book(int ISBN, String title) {
        this.ISBN = ISBN;
        this.title = title;
        this.isCheckedOut = false;
    }

    public int getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCheckedOut() {
        return isCheckedOut;
    }

    public void checkOut() {
        isCheckedOut = true;
    }

    public void returnBook() {
        isCheckedOut = false;
    }
    
    public String toString() {
    	return "ISBN : "+ISBN+" Title : "+title;
    }
}

class Library {
    private List<Book> books;
    private List<Book> borrowed;
    private Map<Integer, Book> bookMap;

    public Library() {
        books = new ArrayList<>();
        bookMap = new HashMap<>();
        borrowed=new LinkedList<>();
    }

    public void addBook(Book book) {
        if (bookMap.containsKey(book.getISBN())) {
            throw new IllegalArgumentException("A book with the same ISBN already exists in the library.");
        }
        books.add(book);
        bookMap.put(book.getISBN(), book);
        
    }

    public void checkOutBook(int ISBN) throws bookCheckedout {
        Book book = bookMap.get(ISBN);
        if (book == null) {
            throw new IllegalArgumentException("The book with ISBN " + ISBN + " is not available in the library.");
        }
        
        if (book.isCheckedOut()) {
            throw new bookCheckedout ("The book with ISBN " + ISBN + " is already checked out.");
        }
        	borrowed.add(book);
        	books.remove(book);
        	book.checkOut();
        	
    }

    public void returnBook(int ISBN) throws booknotcheckedout {
        Book book = bookMap.get(ISBN);
        if (book == null) {
            throw new IllegalArgumentException("The book with ISBN " + ISBN + " is not available in the library.");
        }
        if (!book.isCheckedOut()) {
            throw new booknotcheckedout("The book with ISBN " + ISBN + " has not been checked out.");
        }
    	books.add(book);
    	borrowed.remove(book);

    }
    public void displayBooks() {
    	for (Book book : books) {
    		System.out.println(book);
    	}
    }
}

public class LibraryManagementSystem {
	
    public static void main(String[] args) {
        Library library = new Library();

        
        library.addBook(new Book(1, "Book 1"));
        library.addBook(new Book(2, "Book 2"));
        library.addBook(new Book(3, "Book 3"));
        

        
        try {
            
            library.checkOutBook(3);
            library.checkOutBook(3);
        } catch (bookCheckedout e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            
            library.returnBook(3);
            library.returnBook(2);
            
        } catch (booknotcheckedout e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            
            library.addBook(new Book(1, "Duplicate Book"));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        library.displayBooks();
    }
}
