export type Genre =
	| 'Fiction'
	| 'Non-Fiction'
	| 'Science'
	| 'History'
	| 'Biography'
	| 'Fantasy'
	| 'Mystery'
	| 'Romance';

export interface Book {
	id: number;
	title: string;
	author: string;
	genre: Genre;
	year: number;
	pages: number;
	rating: number; 
	available: boolean;
	description?: string;
}
