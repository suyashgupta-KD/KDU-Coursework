import type { Book } from '../types/book';

export const booksData: Book[] = [
	{
		id: 1,
		title: 'The Great Gatsby',
		author: 'F. Scott Fitzgerald',
		genre: 'Fiction',
		year: 1925,
		pages: 218,
		rating: 4.4,
		available: true,
		description: 'A novel set in the Roaring Twenties.'
	},
	{
		id: 2,
		title: 'A Brief History of Time',
		author: 'Stephen Hawking',
		genre: 'Science',
		year: 1988,
		pages: 256,
		rating: 4.7,
		available: false,
		description: 'A popular-science book on cosmology.'
	},
	{
		id: 3,
		title: 'To Kill a Mockingbird',
		author: 'Harper Lee',
		genre: 'Fiction',
		year: 1960,
		pages: 281,
		rating: 4.8,
		available: true,
		description: 'A novel about racial injustice in the Deep South.'
	},
	{
		id: 4,
		title: 'The Hobbit',
		author: 'J.R.R. Tolkien',
		genre: 'Fantasy',
		year: 1937,
		pages: 310,
		rating: 4.6,
		available: true,
		description: 'A fantasy novel and prelude to The Lord of the Rings.'
	},
	{
		id: 5,
		title: 'The Diary of a Young Girl',
		author: 'Anne Frank',
		genre: 'Biography',
		year: 1947,
		pages: 283,
		rating: 4.5,
		available: false,
		description: 'The writings of Anne Frank during World War II.'
	}
];
