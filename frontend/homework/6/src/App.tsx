import { useState } from 'react';
import { fetchBooksFromData } from './services/libraryService';
import BookList from './components/BookList';
import FilterBar from './components/FilterBar';
import StatsPanel from './components/StatsPanel';
import type { Genre } from './types/book';
import './styles/pages/app.scss';

function App() {
  const allBooks = fetchBooksFromData();
  const [search, setSearch] = useState('');
  const [genre, setGenre] = useState<Genre | ''>('');
  const [rating, setRating] = useState(0);

  // Filter logic
  const filteredBooks = allBooks.filter((book) => {
    const matchesSearch =
      book.title.toLowerCase().includes(search.toLowerCase()) ||
      book.author.toLowerCase().includes(search.toLowerCase());
    const matchesGenre = genre ? book.genre === genre : true;
    const matchesRating = rating ? book.rating >= rating : true;
    return matchesSearch && matchesGenre && matchesRating;
  });

  return (
    <div className="app-container">
      <h2>Book Library Manager</h2>
      <FilterBar
        search={search}
        onSearchChange={setSearch}
        genre={genre}
        onGenreChange={setGenre}
        rating={rating}
        onRatingChange={setRating}
      />
      <div className="app-main">
        <div className="main-content">
          <BookList books={filteredBooks} />
        </div>
        <div className="sidebar">
          <StatsPanel books={filteredBooks} />
        </div>
      </div>
    </div>
  );
}

export default App;
