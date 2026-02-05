import type { Book } from '../types/book';
import '../styles/components/book-card.scss';

type Props = {
	book: Book;
};

export default function BookCard({ book }: Props) {
	return (
		<div className={`book-card ${book.available ? 'available' : 'unavailable'}`}>
			<h3>{book.title}</h3>
			<p><strong>Author:</strong> {book.author}</p>
			<p><strong>Genre:</strong> {book.genre}</p>
			<p><strong>Rating:</strong> {book.rating} / 5</p>
			<p className="status">
				{book.available ? 'Available' : 'Unavailable'}
			</p>
		</div>
	);
}
