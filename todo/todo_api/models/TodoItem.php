<?php

class TodoItem
{
	public $todo_id;
	public $title;
	public $description;
	public $due_date;
	public $is_done;
	
	public function save($username, $userpass)
	{
		// get the username/password hash (secure hash algorithm 1)
		$userhash = sha1("{$username}_{$userpass}");
	}
}

?>