import type { Book } from '../types/book';
import BookCard from './BookCard';
import '../styles/components/book-list.scss';

type Props = {
	books: Book[];
};

export default function BookList({ books }: Props) {
	if (books.length === 0) return <p>No books found.</p>;
	return (
		<div className="book-list">
			{books.map((book) => (
				<BookCard key={book.id} book={book} />
			))}
		</div>
	);
}
