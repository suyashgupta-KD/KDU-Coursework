import { booksData } from '../data/books.data';
import type { Book } from '../types/book';

// Fetch all books from local storage
export function fetchBooksFromData(): Book[] {
	return booksData;
}

// Search books by title or author (case-insensitive)
export function searchBooks(query: string): Book[] {
	const q = query.trim().toLowerCase();
	const results = booksData.filter(
		(book) =>
			book.title.toLowerCase().includes(q) ||
			book.author.toLowerCase().includes(q)
	);
	console.log('Search results:', results);
	return results;
}

// Return only books that are currently available
export function getAvailableBooks(): Book[] {
	return booksData.filter((book) => book.available);
}

// Filter books published between two years (inclusive)
export function getBooksByYearRange(startYear: number, endYear: number): Book[] {
	return booksData.filter(
		(book) => book.year >= startYear && book.year <= endYear
	);
}
