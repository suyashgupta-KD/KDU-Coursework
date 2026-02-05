import type { Book } from '../types/book';
import '../styles/components/stats-panel.scss';

type Props = {
	books: Book[];
};

export default function StatsPanel({ books }: Props) {
	const total = books.length;
	const available = books.filter(b => b.available).length;
	const avgRating = total ? (books.reduce((sum, b) => sum + b.rating, 0) / total).toFixed(2) : '0';
	return (
		<div className="stats-panel">
			<h4>Library Stats</h4>
			<p>Total Books: {total}</p>
			<p>Available: {available}</p>
			<p>Average Rating: {avgRating}</p>
		</div>
	);
}
