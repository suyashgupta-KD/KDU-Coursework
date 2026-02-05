import type { Genre } from '../types/book';
import { genres } from '../types/book';
import '../styles/components/filter-bar.scss';

type Props = {
	search: string;
	onSearchChange: (v: string) => void;
	genre: Genre | '';
	onGenreChange: (v: Genre | '') => void;
	rating: number;
	onRatingChange: (v: number) => void;
};


export default function FilterBar({ search, onSearchChange, genre, onGenreChange, rating, onRatingChange }: Props) {
	return (
		<div className="filter-bar">
			<input
				type="text"
				placeholder="Search by title or author"
				value={search}
				onChange={e => onSearchChange(e.target.value)}
			/>
			<select value={genre} onChange={e => onGenreChange(e.target.value as Genre | '')}>
				<option value="">All Genres</option>
				{genres.map(g => (
					<option key={g} value={g}>{g}</option>
				))}
			</select>
			<select value={rating} onChange={e => onRatingChange(Number(e.target.value))}>
				<option value={0}>All Ratings</option>
				{[5,4,3,2,1].map(r => (
					<option key={r} value={r}>{r}+</option>
				))}
			</select>
		</div>
	);
}
